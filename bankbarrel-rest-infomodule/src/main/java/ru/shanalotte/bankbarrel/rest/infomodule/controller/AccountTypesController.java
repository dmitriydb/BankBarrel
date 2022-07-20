package ru.shanalotte.bankbarrel.rest.infomodule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.bankbarrel.core.dto.CodeAndValuePair;
import ru.shanalotte.bankbarrel.rest.infomodule.service.AccountTypesReader;
import ru.shanalotte.bankbarrel.rest.infomodule.service.caching.CachingManager;

/**
 * Контроллер, который возвращает json с возможными типами банковских счетов 1 и 2 уровня.
 * Должен делегировать запросы к REST API микросервиса Web API Gateway
 * и кэшировать внутри себя ответы
 */
@RestController
public class AccountTypesController {

  public static final String ACCOUNT_CACHE_KEY = "accounttypes";

  private static final Logger logger = LoggerFactory.getLogger(AccountTypesController.class);
  private CachingManager cachingManager;
  private AccountTypesReader accountTypesReader;

  @Autowired
  public AccountTypesController(CachingManager cachingManager,
                                AccountTypesReader accountTypesReader) {
    this.cachingManager = cachingManager;
    this.accountTypesReader = accountTypesReader;
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @CrossOrigin(origins = "http://localhost:8888")
  @GetMapping("/accounttypes")
  @Operation(description = "Получить типы банковских счетов 1 уровня",
      summary = "Получить типы счетов 1 уровня")
  public List<CodeAndValuePair> getAccountTypes() {
    logger.info("GET /accounttypes");
    if (accountTypesAreCached()) {
      return cachedAccountTypes();
    }
    List<CodeAndValuePair> accountTypes = accountTypesReader.getAccountTypes();
    cacheAccountTypes(accountTypes);
    return accountTypes;
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @CrossOrigin(origins = "http://localhost:8888")
  @GetMapping("/accounttype/{code}/additionaltypes")
  @Operation(description = "Получить типы банковских счетов 2 уровня",
      summary = "Получить типы счетов 2 уровня")
  @ApiResponse(responseCode = "404", description = "Возвращает в случае, "
      + "если тип банковского счета 1 уровня с таким кодом не существует")
  public ResponseEntity<List<CodeAndValuePair>> getAdditionalAccountTypes(
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
    String cacheKey = ACCOUNT_CACHE_KEY + "." + code;
    if (cachingManager.hasKey(cacheKey)) {
      return new ResponseEntity<>(
          convertCachedValuesToResponse(cachedAdditionalAccountTypes(code)), HttpStatus.OK);
    }
    List<CodeAndValuePair> codeAndValuePairs = accountTypesReader.getAdditionalAccountTypes(code);
    cachingManager.cacheValue(cacheKey, convertResponseToCachedValues(codeAndValuePairs));
    return new ResponseEntity<>(codeAndValuePairs, HttpStatus.OK);
  }

  private Map<String, String> cachedAdditionalAccountTypes(String code) {
    return (Map<String, String>) cachingManager.getValueFromCache(ACCOUNT_CACHE_KEY + "." + code);
  }

  private Map<String, String> convertResponseToCachedValues(
      List<CodeAndValuePair> codeAndValuePairs) {
    Map<String, String> cachedValues = new HashMap<>();
    for (CodeAndValuePair codeAndValuePair : codeAndValuePairs) {
      cachedValues.put(codeAndValuePair.getCode(), codeAndValuePair.getValue());
    }
    return cachedValues;
  }


  private List<CodeAndValuePair> cachedAccountTypes() {
    Map<String, String> cachedValues = (Map<String, String>)
        cachingManager.getValueFromCache(ACCOUNT_CACHE_KEY);
    logger.info("RETURNING CACHED VALUE {}", cachedValues);
    return convertCachedValuesToResponse(cachedValues);
  }

  private void cacheAccountTypes(List<CodeAndValuePair> codeAndValuePairs) {
    cachingManager.cacheValue(ACCOUNT_CACHE_KEY, convertResponseToCachedValues(codeAndValuePairs));
  }

  private boolean accountTypesAreCached() {
    return cachingManager.hasKey(ACCOUNT_CACHE_KEY);
  }

  private List<CodeAndValuePair> convertCachedValuesToResponse(Map<String, String> cachedValues) {
    List<CodeAndValuePair> codeAndValuePairs = new ArrayList<>();
    cachedValues.forEach((key, value) -> codeAndValuePairs.add(new CodeAndValuePair(key, value)));
    return codeAndValuePairs;
  }

}
