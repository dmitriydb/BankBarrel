package ru.shanalotte.bankbarrel.webapp.dao.impl;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankClientDao;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceUrlBuilder;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.WebApiServiceRegistryProxy;

/**
 * DAO для клиентов банка.
 * Использует интеграцию с REST через Web Api Gateway.
 */
@Repository
@Profile({"dev", "production"})
public class RealClientDao implements BankClientDao {

  private ServiceUrlBuilder serviceUrlBuilder;
  private WebApiServiceRegistryProxy serviceRegistryProxy;

  public RealClientDao(ServiceUrlBuilder serviceUrlBuilder,
                       WebApiServiceRegistryProxy serviceRegistryProxy) {
    this.serviceUrlBuilder = serviceUrlBuilder;
    this.serviceRegistryProxy = serviceRegistryProxy;
  }

  @Override
  public int count() {
    RestTemplate restTemplate = new RestTemplate();
    String url =
        serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/clients";
    BankClientDto[] list = restTemplate.getForEntity(
        URI.create(url), BankClientDto[].class).getBody();
    return list.length;
  }

  @Override
  public void save(BankClientDto newBankClient) {
    RestTemplate restTemplate = new RestTemplate();
    String url =
        serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/clients";
    restTemplate.postForEntity(URI.create(url), newBankClient, BankClientDto.class);
  }

  @Override
  public BankClientDto findByGivenName(String givenName) {
    RestTemplate restTemplate = new RestTemplate();
    String url =
        serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/clients";
    BankClientDto[] list = restTemplate.getForEntity(
        URI.create(url), BankClientDto[].class).getBody();
    return Arrays.stream(list).filter(e -> e.getGivenName().equals(givenName)).findFirst().get();
  }

  @Override
  public List<BankAccountDto> accounts(BankClientDto clientDto) {
    RestTemplate restTemplate = new RestTemplate();
    String url =
        serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/clients";
    BankClientDto[] list = restTemplate.getForEntity(
        URI.create(url), BankClientDto[].class).getBody();
    BankClientDto dto = Arrays.stream(list).filter(e -> e.equals(clientDto)).findFirst().get();
    url = serviceUrlBuilder.buildServiceUrl(
        serviceRegistryProxy.getWebApiInfo()) + "/clients/" + dto.getId() + "/accounts";
    BankAccountDto[] accountsList = restTemplate.getForEntity(
        URI.create(url), BankAccountDto[].class).getBody();
    return Arrays.stream(accountsList).collect(Collectors.toList());
  }

  @Override
  public Long idByDto(BankClientDto dto) {
    RestTemplate restTemplate = new RestTemplate();
    String url =
        serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/clients";
    BankClientDto[] list = restTemplate.getForEntity(
        URI.create(url), BankClientDto[].class).getBody();
    Optional<BankClientDto> result = Arrays.stream(list).filter(e -> e.equals(dto)).findFirst();
    return result.map(BankClientDto::getId).orElse(null);
  }


}
