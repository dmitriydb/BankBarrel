package ru.shanalotte.bankbarrel.webapp.controller.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.ListingDtoItem;
import ru.shanalotte.bankbarrel.core.dto.serviceregistry.RegisteredServiceInfo;
import ru.shanalotte.bankbarrel.webapp.service.jwt.JwtTokenStorer;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceRegistryProxy;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceUrlBuilder;

/**
 * Контроллер, который возвращает json с возможными типами банковских счетов 1 и 2 уровня.
 */
@RestController
public class AccountTypesController {

  private ServiceRegistryProxy serviceRegistryProxy;
  private ServiceUrlBuilder serviceUrlBuilder;
  private JwtTokenStorer jwtTokenStorer;

  private static final Logger logger = LoggerFactory.getLogger(AccountTypesController.class);


  public AccountTypesController(ServiceRegistryProxy serviceRegistryProxy,
                                ServiceUrlBuilder serviceUrlBuilder,
                                JwtTokenStorer jwtTokenStorer) {
    this.serviceRegistryProxy = serviceRegistryProxy;
    this.serviceUrlBuilder = serviceUrlBuilder;
    this.jwtTokenStorer = jwtTokenStorer;
  }

  /**
   * Возвращает json со всеми типами счетов 1 уровня.
   */
  @CrossOrigin(origins = "http://localhost:8890")
  @GetMapping("/accounttypes")
  public List<ListingDtoItem> accountTypes() {
    logger.info("GET /accounttypes");
    List<ListingDtoItem> listingDtoItems = new ArrayList<>();
    RestTemplate restTemplate = new RestTemplate();
    RegisteredServiceInfo registeredServiceInfo = serviceRegistryProxy.getRestInfoModuleInfo();
    logger.info(registeredServiceInfo.toString());
    String url = serviceUrlBuilder.buildServiceUrl(registeredServiceInfo);
    logger.info(url);
    HttpHeaders headers = new HttpHeaders();
    System.out.println("Token = " + jwtTokenStorer.getToken());
    headers.set("Authorization", jwtTokenStorer.getToken());
    HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
    ResponseEntity<List> response = restTemplate.exchange(
        url + "/accounttypes", HttpMethod.GET, requestEntity, List.class);
    return response.getBody();
  }

  /**
   * Возвращает json с типами счетов 2 уровня.
   *
   * @param code код типа 1 уровня
   */
  @CrossOrigin(origins = "http://localhost:8890")
  @GetMapping("/accounttype/{code}/additionaltypes")
  public ResponseEntity<List<ListingDtoItem>> additionalTypes(@PathVariable("code") String code) {
    logger.info("GET /accounttype/{}/additionaltypes", code);
    List<ListingDtoItem> listingDtoItems = new ArrayList<>();
    RestTemplate restTemplate = new RestTemplate();
    RegisteredServiceInfo registeredServiceInfo = serviceRegistryProxy.getRestInfoModuleInfo();
    String url = serviceUrlBuilder.buildServiceUrl(registeredServiceInfo);
    System.out.println("Token = " + jwtTokenStorer.getToken());
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", jwtTokenStorer.getToken());
    HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
    ResponseEntity<ListingDtoItem[]> response = restTemplate.exchange(
        url + "/accounttype/" + code + "/additionaltypes", HttpMethod.GET, requestEntity, ListingDtoItem[].class);
    return new ResponseEntity<>(
        Arrays.stream(response.getBody()).collect(Collectors.toList()), HttpStatus.OK
    );
  }
}
