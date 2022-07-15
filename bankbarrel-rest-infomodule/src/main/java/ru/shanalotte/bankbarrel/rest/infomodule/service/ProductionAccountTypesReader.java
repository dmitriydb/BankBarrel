package ru.shanalotte.bankbarrel.rest.infomodule.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.CodeAndValuePair;
import ru.shanalotte.bankbarrel.core.dto.serviceregistry.AbstractAccountTypeDto;
import ru.shanalotte.bankbarrel.rest.infomodule.jwt.JwtTokenStorer;
import ru.shanalotte.bankbarrel.rest.infomodule.service.serviceregistry.ServiceRegistryProxy;
import ru.shanalotte.bankbarrel.rest.infomodule.service.serviceregistry.ServiceUrlBuilder;

@Service
@Profile("production")
public class ProductionAccountTypesReader implements AccountTypesReader{

  private JwtTokenStorer jwtTokenStorer;
  private ServiceRegistryProxy serviceRegistryProxy;
  private ServiceUrlBuilder serviceUrlBuilder;
  private RestTemplate restTemplate;

  @Autowired
  public ProductionAccountTypesReader(JwtTokenStorer jwtTokenStorer, ServiceRegistryProxy serviceRegistryProxy, ServiceUrlBuilder serviceUrlBuilder, RestTemplate restTemplate) {
    this.jwtTokenStorer = jwtTokenStorer;
    this.serviceRegistryProxy = serviceRegistryProxy;
    this.serviceUrlBuilder = serviceUrlBuilder;
    this.restTemplate = restTemplate;
  }

  @Override
  public List<CodeAndValuePair> getAccountTypes() {
    String url = serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/accounttypes";
    return processRequest(url);
  }

  @Override
  public List<CodeAndValuePair> getAdditionalAccountTypes(String accountTypeCode) {
    String url = serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/accounttypes/" + accountTypeCode + "/additionaltypes";
    return processRequest(url);
  }

  private HttpEntity<Void> createAuthorizedHttpEntity() {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(jwtTokenStorer.getToken());
    return new HttpEntity<>(headers);
  }

  private List<CodeAndValuePair> processRequest(String url) {
    List<CodeAndValuePair> codeAndValuePairs = new ArrayList<>();
    HttpEntity<Void> entity = createAuthorizedHttpEntity();
    ResponseEntity<AbstractAccountTypeDto[]> responseEntity = restTemplate.exchange(
        url, HttpMethod.GET, entity, AbstractAccountTypeDto[].class);
    Arrays.stream(responseEntity.getBody()).forEach(
        dto -> codeAndValuePairs.add(new CodeAndValuePair(dto.getType(), dto.getDescription()))
    );
    return codeAndValuePairs;
  }

}
