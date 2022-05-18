package ru.shanalotte.bankbarrel.webapp.dao.impl;

import java.net.URI;
import java.util.Arrays;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.config.FakeAccountNumberGenerator;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankAccountDao;
import ru.shanalotte.bankbarrel.webapp.dto.serviceregistry.RegisteredServiceInfo;
import ru.shanalotte.bankbarrel.webapp.dto.transfer.TransferDto;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotFound;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.IServiceUrlBuilder;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceRegistryProxy;

@Repository
@Primary
public class RealBankAccountDao implements BankAccountDao {

  private ServiceRegistryProxy serviceRegistryProxy;
  private IServiceUrlBuilder serviceUrlBuilder;
  private FakeAccountNumberGenerator fakeAccountNumberGenerator;

  public RealBankAccountDao(ServiceRegistryProxy serviceRegistryProxy, IServiceUrlBuilder serviceUrlBuilder, FakeAccountNumberGenerator fakeAccountNumberGenerator) {
    this.serviceRegistryProxy = serviceRegistryProxy;
    this.serviceUrlBuilder = serviceUrlBuilder;
    this.fakeAccountNumberGenerator = fakeAccountNumberGenerator;
  }

  @Override
  public void save(BankAccountDto account) {
    RegisteredServiceInfo webApiInfo = serviceRegistryProxy.getWebApiInfo();
    String url = serviceUrlBuilder.buildUrl(webApiInfo) + "/accounts";
    RestTemplate restTemplate = new RestTemplate();

    for (int i = 0; i < 100; i++) {
      try {
        ResponseEntity<BankAccountDto> bankAccountDtoResponseEntity = restTemplate.postForEntity(URI.create(url), account, BankAccountDto.class);
        break;
      } catch (Exception ex) {
        account.setNumber(fakeAccountNumberGenerator.generateNumber());
      }
    }
  }

  @Override
  public BankAccountDto findByNumber(String number) {
    RestTemplate restTemplate = new RestTemplate();
    String url = serviceUrlBuilder.buildUrl(serviceRegistryProxy.getWebApiInfo()) + "/accounts";
    BankAccountDto[] list = restTemplate.getForEntity(URI.create(url), BankAccountDto[].class).getBody();
    return Arrays.stream(list).filter(e -> e.getNumber().equals(number)).findFirst().orElse(null);
  }

  @Override
  public void delete(BankAccountDto account) {
    RestTemplate restTemplate = new RestTemplate();
    String url = serviceUrlBuilder.buildUrl(serviceRegistryProxy.getWebApiInfo()) + "/accounts";
    BankAccountDto[] list = restTemplate.getForEntity(URI.create(url), BankAccountDto[].class).getBody();
    BankAccountDto dto = Arrays.stream(list).filter(e -> e.getNumber().equals(account.getNumber())).findFirst().get();
    System.out.println("Deleting " + url + "/" + dto.getIdentifier());
    restTemplate.delete(url + "/" + dto.getIdentifier());
  }

  @Override
  public BankAccountDto findByTransferDto(TransferDto dto) throws BankAccountNotFound {
    RestTemplate restTemplate = new RestTemplate();
    String url = serviceUrlBuilder.buildUrl(serviceRegistryProxy.getWebApiInfo()) + "/accounts";
    BankAccountDto[] list = restTemplate.getForEntity(URI.create(url), BankAccountDto[].class).getBody();
    return Arrays.stream(list).filter(e -> e.getNumber().equals(dto.getAccountNumber())).findFirst().get();
  }
}
