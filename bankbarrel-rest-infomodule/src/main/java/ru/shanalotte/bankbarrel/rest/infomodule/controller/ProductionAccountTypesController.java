package ru.shanalotte.bankbarrel.rest.infomodule.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
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
import ru.shanalotte.bankbarrel.core.dto.AccountAdditionalTypeDto;
import ru.shanalotte.bankbarrel.core.dto.AccountTypeDto;
import ru.shanalotte.bankbarrel.core.dto.ListingDtoItem;
import ru.shanalotte.bankbarrel.core.service.EnumToListingDtoItemConverter;
import ru.shanalotte.bankbarrel.rest.infomodule.service.JwtTokenStorer;
import ru.shanalotte.bankbarrel.rest.infomodule.service.serviceregistry.ServiceRegistryProxy;
import ru.shanalotte.bankbarrel.rest.infomodule.service.serviceregistry.ServiceUrlBuilder;

/**
 * Контроллер, который возвращает json с возможными типами банковских счетов 1 и 2 уровня.
 */
@RestController
@Profile({"production"})
public class ProductionAccountTypesController {

  public static final String REDIS_ACCOUNTS_KEY = "accounttypes";

  private static final Logger logger = LoggerFactory.getLogger(DevAccountTypesController.class);
  private EnumToListingDtoItemConverter enumToListingDtoItemConverter;
  private JwtTokenStorer jwtTokenStorer;
  private ServiceRegistryProxy serviceRegistryProxy;
  private ServiceUrlBuilder serviceUrlBuilder;
  private RedisTemplate<String, Object> redisTemplate;

  @Autowired
  public ProductionAccountTypesController(EnumToListingDtoItemConverter enumToListingDtoItemConverter,
                                          JwtTokenStorer jwtTokenStorer,
                                          ServiceRegistryProxy serviceRegistryProxy,
                                          ServiceUrlBuilder serviceUrlBuilder,
                                          RedisTemplate<String, Object> redisTemplate) {
    this.enumToListingDtoItemConverter = enumToListingDtoItemConverter;
    this.jwtTokenStorer = jwtTokenStorer;
    this.serviceRegistryProxy = serviceRegistryProxy;
    this.serviceUrlBuilder = serviceUrlBuilder;
    this.redisTemplate = redisTemplate;
  }

  /**
   * Возвращает json со всеми типами счетов 1 уровня.
   */
  @CrossOrigin(origins = "http://localhost:8888")
  @GetMapping("/accounttypes")
  @Operation(description = "Получить типы банковских счетов 1 уровня", summary = "Получить типы счетов 1 уровня")
  public List<ListingDtoItem> accountTypes() {
    logger.info("GET /accounttypes");
    if (redisTemplate.hasKey(REDIS_ACCOUNTS_KEY)) {
      Map<String, String> cachedValues = (Map<String, String>) redisTemplate.opsForValue().get(REDIS_ACCOUNTS_KEY);
      logger.info("RETURNING CACHED VALUE FROM REDIS {}", cachedValues);
      List<ListingDtoItem> listingDtoItems = new ArrayList<>();
      cachedValues.forEach((key, value) -> listingDtoItems.add(new ListingDtoItem(key, value)));
      return listingDtoItems;
    }
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(jwtTokenStorer.getToken());
    HttpEntity<Void> entity = new HttpEntity<>(headers);
    String url = serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/accounttypes";
    ResponseEntity<AccountTypeDto[]> responseEntity = restTemplate.exchange(
        url, HttpMethod.GET, entity, AccountTypeDto[].class);
    List<ListingDtoItem> listingDtoItems = new ArrayList<>();
    Arrays.stream(responseEntity.getBody()).forEach(
        dto -> listingDtoItems.add(new ListingDtoItem(dto.getType(), dto.getDescription()))
    );
    if (!redisTemplate.hasKey(REDIS_ACCOUNTS_KEY)) {
      Map<String, String> cachedValues = new HashMap<>();
      for (ListingDtoItem listingDtoItem : listingDtoItems) {
        cachedValues.put(listingDtoItem.getCode(), listingDtoItem.getValue());
      }
      redisTemplate.opsForValue().set(REDIS_ACCOUNTS_KEY, cachedValues);
    }
    return listingDtoItems;
  }

  /**
   * Возвращает json с типами счетов 2 уровня.
   *
   * @param code код типа 1 уровня
   */
  @CrossOrigin(origins = "http://localhost:8888")
  @GetMapping("/accounttype/{code}/additionaltypes")
  @Operation(description = "Получить типы банковских счетов 2 уровня", summary = "Получить типы счетов 2 уровня")
  @ApiResponse(responseCode = "404", description = "Возвращает в случае, "
      + "если тип банковского счета 1 уровня с таким кодом не существует")
  public ResponseEntity<List<ListingDtoItem>> additionalTypes(
      @Parameter(description = "Тип счета 1 уровня", examples = {
          @ExampleObject(
              name = "CHECKING",
              description = "Получить список типов сберегательных счетов",
              value = "CHECKING",
              summary = "CHECKING"
          ),
          @ExampleObject(
              name = "SAVING",
              description = "Получить список типов накопительных счетов",
              value = "SAVING",
              summary = "SAVING"
          )
      })
      @PathVariable("code") String code) {
    logger.info("GET /accounttype/{}/additionaltypes", code);
    if (redisTemplate.hasKey(REDIS_ACCOUNTS_KEY + "." + code)) {
      Map<String, String> cachedValues = (Map<String, String>) redisTemplate.opsForValue().get(REDIS_ACCOUNTS_KEY + "." + code);
      logger.info("RETURNING CACHED VALUE FROM REDIS {}", cachedValues);
      List<ListingDtoItem> listingDtoItems = new ArrayList<>();
      cachedValues.forEach((key, value) -> listingDtoItems.add(new ListingDtoItem(key, value)));
      return new ResponseEntity<>(listingDtoItems, HttpStatus.OK);
    }
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(jwtTokenStorer.getToken());
    HttpEntity<Void> entity = new HttpEntity<>(headers);
    String url = serviceUrlBuilder.buildServiceUrl(serviceRegistryProxy.getWebApiInfo()) + "/accounttypes/" + code + "/additionaltypes";
    ResponseEntity<AccountAdditionalTypeDto[]> responseEntity = restTemplate.exchange(
        url, HttpMethod.GET, entity, AccountAdditionalTypeDto[].class);
    List<ListingDtoItem> listingDtoItems = new ArrayList<>();
    Arrays.stream(responseEntity.getBody()).forEach(
        dto -> listingDtoItems.add(new ListingDtoItem(dto.getType(), dto.getDescription()))
    );
    if (!redisTemplate.hasKey(REDIS_ACCOUNTS_KEY + "." + code)) {
      Map<String, String> cachedValues = new HashMap<>();
      for (ListingDtoItem listingDtoItem : listingDtoItems) {
        cachedValues.put(listingDtoItem.getCode(), listingDtoItem.getValue());
      }
      redisTemplate.opsForValue().set(REDIS_ACCOUNTS_KEY + "." + code, cachedValues);
    }
    return new ResponseEntity<>(listingDtoItems, HttpStatus.OK);
  }
}
