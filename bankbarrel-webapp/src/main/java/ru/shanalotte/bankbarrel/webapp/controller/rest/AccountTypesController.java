package ru.shanalotte.bankbarrel.webapp.controller.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.ListingDto;
import ru.shanalotte.bankbarrel.core.dto.ListingDtoItem;
import ru.shanalotte.bankbarrel.webapp.dto.serviceregistry.RegisteredServiceInfo;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.IServiceUrlBuilder;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceRegistryProxy;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceUrlBuilder;

/**
 * Контроллер, который возвращает json с возможными типами банковских счетов 1 и 2 уровня.
 */
@RestController
public class AccountTypesController {

  private ServiceRegistryProxy serviceRegistryProxy;
  private IServiceUrlBuilder iServiceUrlBuilder;

  public AccountTypesController(ServiceRegistryProxy serviceRegistryProxy, IServiceUrlBuilder iServiceUrlBuilder) {
    this.serviceRegistryProxy = serviceRegistryProxy;
    this.iServiceUrlBuilder = iServiceUrlBuilder;
  }

  /**
   * Возвращает json со всеми типами счетов 1 уровня.
   */
  @CrossOrigin(origins = "http://localhost:8890")
  @GetMapping("/accounttypes")
  public List<ListingDtoItem> accountTypes() {
    List<ListingDtoItem> listingDtoItems = new ArrayList<>();
    RestTemplate restTemplate = new RestTemplate();
    RegisteredServiceInfo registeredServiceInfo = serviceRegistryProxy.getRestInfoModuleInfo();
    String url = iServiceUrlBuilder.buildUrl(registeredServiceInfo);
    listingDtoItems = restTemplate.getForObject(URI.create(url + "/accounttypes"), List.class);
    return listingDtoItems;
  }

  /**
   * Возвращает json с типами счетов 2 уровня.
   *
   * @param code код типа 1 уровня
   */
  @CrossOrigin(origins = "http://localhost:8890")
  @GetMapping("/accounttype/{code}/additionaltypes")
  public ResponseEntity<List<ListingDtoItem>> additionalTypes(@PathVariable("code") String code) {
    List<ListingDtoItem> listingDtoItems = new ArrayList<>();
    RestTemplate restTemplate = new RestTemplate();
    RegisteredServiceInfo registeredServiceInfo = serviceRegistryProxy.getRestInfoModuleInfo();
    String url = iServiceUrlBuilder.buildUrl(registeredServiceInfo);
    ResponseEntity<ListingDtoItem[]> response =
        restTemplate.getForEntity(
            url + "/accounttype/" + code + "/additionaltypes",
            ListingDtoItem[].class);
    return new ResponseEntity<>(
        Arrays.stream(response.getBody()).collect(Collectors.toList()), HttpStatus.OK
    );
  }
}
