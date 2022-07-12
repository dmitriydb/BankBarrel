package ru.shanalotte.bankbarrel.webapp.dao.impl;

import java.net.URI;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.core.dto.serviceregistry.RegisteredServiceInfo;
import ru.shanalotte.bankbarrel.webapp.config.FakeAccountNumberGenerator;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankAccountDao;
import ru.shanalotte.bankbarrel.webapp.dto.account.AccountOpeningDto;
import ru.shanalotte.bankbarrel.webapp.dto.transfer.TransferDto;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotFound;
import ru.shanalotte.bankbarrel.webapp.service.jwt.JwtTokenStorer;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceUrlBuilder;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.WebApiServiceRegistryProxy;

/**
 * DAO для банковских счетов в веб-приложении.
 * Использует интеграцию с микросервисом bb-webapi-gateway через REST.
 */
@Repository
@Profile({"production"})
public class RealBankAccountDao implements BankAccountDao {

  private static final Logger logger = LoggerFactory.getLogger(RealBankAccountDao.class);
  private WebApiServiceRegistryProxy serviceRegistryProxy;
  private ServiceUrlBuilder serviceUrlBuilder;
  private FakeAccountNumberGenerator fakeAccountNumberGenerator;
  private JwtTokenStorer jwtTokenStorer;

  public RealBankAccountDao(WebApiServiceRegistryProxy serviceRegistryProxy,
                            ServiceUrlBuilder serviceUrlBuilder,
                            FakeAccountNumberGenerator fakeAccountNumberGenerator,
                            JwtTokenStorer jwtTokenStorer) {
    this.serviceRegistryProxy = serviceRegistryProxy;
    this.serviceUrlBuilder = serviceUrlBuilder;
    this.fakeAccountNumberGenerator = fakeAccountNumberGenerator;
    this.jwtTokenStorer = jwtTokenStorer;
  }

  @Override
  public void save(BankAccountDto account) {
    RegisteredServiceInfo webApiInfo = serviceRegistryProxy.getWebApiInfo();
    String url = serviceUrlBuilder.buildServiceUrl(webApiInfo) + "/accounts";
    RestTemplate restTemplate = new RestTemplate();

    for (int i = 0; i < 100; i++) {
      try {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtTokenStorer.getToken());
        HttpEntity<BankAccountDto> entity = new HttpEntity<>(account, headers);
        ResponseEntity<BankAccountDto> bankAccountDtoResponseEntity =
            restTemplate.postForEntity(URI.create(url), entity, BankAccountDto.class);
        break;
      } catch (Exception ex) {
        account.setNumber(fakeAccountNumberGenerator.generateNumber());
      }
    }
  }

  @Override
  public BankAccountDto findByNumber(String number) {
    RestTemplate restTemplate = new RestTemplate();
    String url =
        serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/accounts";
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(jwtTokenStorer.getToken());
    HttpEntity<Void> entity = new HttpEntity<>(headers);
    ResponseEntity<BankAccountDto[]> response = restTemplate.exchange(
        url, HttpMethod.GET, entity, BankAccountDto[].class);
    BankAccountDto[] list =
       response.getBody();
    return Arrays.stream(list).filter(e -> e.getNumber().equals(number)).findFirst().orElse(null);
  }

  @Override
  public void delete(BankAccountDto account) {
    RestTemplate restTemplate = new RestTemplate();
    String url =
        serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/accounts";
    BankAccountDto[] list = restTemplate.getForEntity(URI.create(url),
        BankAccountDto[].class).getBody();
    BankAccountDto dto = Arrays.stream(list).filter(
        e -> e.getNumber().equals(account.getNumber())).findFirst().get();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(jwtTokenStorer.getToken());
    HttpEntity<Void> entity = new HttpEntity<>(headers);
    restTemplate.exchange(url + "/" + dto.getIdentifier(), HttpMethod.DELETE, entity, String.class);
  }

  @Override
  public BankAccountDto findByTransferDto(TransferDto dto) throws BankAccountNotFound {
    RestTemplate restTemplate = new RestTemplate();
    String url =
        serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/accounts";
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(jwtTokenStorer.getToken());
    HttpEntity<Void> entity = new HttpEntity<>(headers);
    ResponseEntity<BankAccountDto[]> response = restTemplate.exchange(
        url, HttpMethod.GET, entity, BankAccountDto[].class);
    BankAccountDto[] list = response.getBody();
    return Arrays.stream(list).filter(e -> e.getNumber().equals(
        dto.getAccountNumber())).findFirst().get();
  }

  @Override
  public void createAccount(AccountOpeningDto dto, BankClientDto bankClient) {
    String url =
        serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/clients";
    logger.debug("Using url {}", url);
    logger.debug("Bank client dto: {}", bankClient);
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(jwtTokenStorer.getToken());
    HttpEntity<Void> entity = new HttpEntity<>(headers);
    ResponseEntity<BankClientDto[]> responseEntity = restTemplate.exchange(
        url, HttpMethod.GET, entity, BankClientDto[].class);
    BankClientDto client = Arrays.stream(
        responseEntity.getBody()).filter(e -> e.getId().equals(bankClient.getId())).findFirst().get();
    BankAccountDto bankAccount = new BankAccountDto();
    bankAccount.setOwner(client.getId());
    bankAccount.setNumber(fakeAccountNumberGenerator.generateNumber());
    bankAccount.setDescription(bankAccount.getNumber());
    bankAccount.setCurrency(dto.getCurrency());
    bankAccount.setType(dto.getAccountType());
    bankAccount.setAdditionalType(dto.getAccountAdditionalType());
    save(bankAccount);
  }
}
