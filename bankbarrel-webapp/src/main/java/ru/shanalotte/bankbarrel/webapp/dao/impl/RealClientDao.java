package ru.shanalotte.bankbarrel.webapp.dao.impl;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankClientDao;
import ru.shanalotte.bankbarrel.webapp.service.jwt.JwtTokenStorer;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceUrlBuilder;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.WebApiServiceRegistryProxy;

/**
 * DAO для клиентов банка.
 * Использует интеграцию с REST через Web Api Gateway.
 */
@Repository
@Profile({"production"})
public class RealClientDao implements BankClientDao {

  private ServiceUrlBuilder serviceUrlBuilder;
  private WebApiServiceRegistryProxy serviceRegistryProxy;
  private JwtTokenStorer jwtTokenStorer;

  @Autowired
  public RealClientDao(ServiceUrlBuilder serviceUrlBuilder,
                       WebApiServiceRegistryProxy serviceRegistryProxy,
                       JwtTokenStorer jwtTokenStorer) {
    this.serviceUrlBuilder = serviceUrlBuilder;
    this.serviceRegistryProxy = serviceRegistryProxy;
    this.jwtTokenStorer = jwtTokenStorer;
  }

  @Override
  public int count() {
    RestTemplate restTemplate = new RestTemplate();
    String url =
        serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/clients";
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(jwtTokenStorer.getToken());
    HttpEntity<Void> entity = new HttpEntity<>(headers);
    ResponseEntity<BankClientDto[]> responseEntity = restTemplate.exchange(
        url, HttpMethod.GET, entity, BankClientDto[].class);
    BankClientDto[] list = responseEntity.getBody();
    return list.length;
  }

  @Override
  public void save(BankClientDto newBankClient) {
    RestTemplate restTemplate = new RestTemplate();
    String url =
        serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/clients";
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(jwtTokenStorer.getToken());
    HttpEntity<BankClientDto> entity = new HttpEntity<>(newBankClient, headers);
    restTemplate.postForEntity(URI.create(url), entity, BankClientDto.class);
  }

  @Override
  public BankClientDto findByGivenName(String givenName) {
    RestTemplate restTemplate = new RestTemplate();
    String url =
        serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/clients";
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(jwtTokenStorer.getToken());
    HttpEntity<Void> entity = new HttpEntity<>(headers);
    ResponseEntity<BankClientDto[]> responseEntity = restTemplate.exchange(
        url, HttpMethod.GET, entity, BankClientDto[].class);
    BankClientDto[] list = responseEntity.getBody();
    return Arrays.stream(list).filter(e -> e.getGivenName().equals(givenName)).findFirst().get();
  }

  @Override
  public List<BankAccountDto> accounts(BankClientDto clientDto) {
    RestTemplate restTemplate = new RestTemplate();
    String url =
        serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/clients";
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(jwtTokenStorer.getToken());
    HttpEntity<Void> entity = new HttpEntity<>(headers);
    ResponseEntity<BankClientDto[]> responseEntity = restTemplate.exchange(
        url, HttpMethod.GET, entity, BankClientDto[].class);
    BankClientDto[] list = responseEntity.getBody();
    BankClientDto dto = Arrays.stream(list).filter(e -> e.getId().equals(clientDto.getId())).findFirst().get();
    url = serviceUrlBuilder.buildServiceUrl(
        serviceRegistryProxy.getWebApiInfo()) + "/clients/" + dto.getId() + "/accounts";
    ResponseEntity<BankAccountDto[]> accountsEntity = restTemplate.exchange(
        url, HttpMethod.GET, entity, BankAccountDto[].class);
    BankAccountDto[] accountsList = accountsEntity.getBody();
    return Arrays.stream(accountsList).collect(Collectors.toList());
  }

  @Override
  public Long idByDto(BankClientDto dto) {
    if (dto.getId() != null) {
      return dto.getId();
    }
    RestTemplate restTemplate = new RestTemplate();
    String url =
        serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/clients";
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(jwtTokenStorer.getToken());
    HttpEntity<Void> entity = new HttpEntity<>(headers);
    ResponseEntity<BankClientDto[]> responseEntity = restTemplate.exchange(
        url, HttpMethod.GET, entity, BankClientDto[].class);
    BankClientDto[] list = responseEntity.getBody();
    Optional<BankClientDto> result = Arrays.stream(list).filter(e -> e.equals(dto)).findFirst();
    return result.map(BankClientDto::getId).orElse(null);
  }


}
