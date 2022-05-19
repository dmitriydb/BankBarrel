package ru.shanalotte.bankbarrel.appserver.controller;

import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.bankbarrel.appserver.domain.Currency;
import ru.shanalotte.bankbarrel.appserver.repository.CurrencyDao;
import ru.shanalotte.bankbarrel.appserver.repository.CurrencyRateDao;
import ru.shanalotte.bankbarrel.core.domain.CurrencyRateRule;
import ru.shanalotte.bankbarrel.core.dto.CurrencyDto;
import ru.shanalotte.bankbarrel.core.dto.CurrencyRateDto;

/**
 * Контроллер для доступа к информации о валютах.
 */
@RestController
public class CurrenciesController {

  private static final Logger logger = LoggerFactory.getLogger(CurrenciesController.class);
  private CurrencyDao currencyDao;
  private CurrencyRateDao currencyRateDao;

  /**
   * Конструктор со всеми зависимостями.
   */
  public CurrenciesController(CurrencyDao currencyDao, CurrencyRateDao currencyRateDao) {
    this.currencyDao = currencyDao;
    this.currencyRateDao = currencyRateDao;
  }

  /**
   * Добавление курса валюты.
   */
  @PostMapping("/currencies/{currency}/rate")
  public ResponseEntity<CurrencyRateDto> createCurrencyRate(
      @PathVariable("currency") String currency, @RequestBody CurrencyRateDto dto)
      throws JsonProcessingException {
    logger.info("POST /currencies/{}/rate {}", currency, new ObjectMapper().writeValueAsString(dto));
    Currency currencyEntity = currencyDao.findByCode(currency);
    if (currencyEntity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    CurrencyRateRule currencyRateRule = new CurrencyRateRule();
    currencyRateRule.setCurrency(currency);
    currencyRateRule.setRate(dto.getRate());
    currencyRateRule.setMore(dto.isMore());
    currencyRateDao.save(currencyRateRule);
    CurrencyRateDto result = new CurrencyRateDto();
    result.setCurrency(currencyRateRule.getCurrency());
    result.setRate(currencyRateRule.getRate());
    result.setMore(currencyRateRule.isMore());
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  /**
   * Изменение курса валюты.
   */
  @PutMapping("/currencies/{currency}/rate")
  public ResponseEntity<CurrencyRateDto> updateCurrencyRate(
      @PathVariable("currency") String currency,
      @RequestBody CurrencyRateDto dto) throws JsonProcessingException {
    logger.info("PUT /currencies/{}/rate {}", currency, new ObjectMapper().writeValueAsString(dto));
    Currency currencyEntity = currencyDao.findByCode(currency);
    if (currencyEntity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    CurrencyRateRule currencyRateRule = new CurrencyRateRule();
    currencyRateRule.setCurrency(currency);
    currencyRateRule.setRate(dto.getRate());
    currencyRateRule.setMore(dto.isMore());
    currencyRateDao.save(currencyRateRule);
    CurrencyRateDto result = new CurrencyRateDto();
    result.setCurrency(currencyRateRule.getCurrency());
    result.setRate(currencyRateRule.getRate());
    result.setMore(currencyRateRule.isMore());
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  /**
   * Информация о курсе валюты.
   */
  @GetMapping("/currencies/{currency}/rate")
  public ResponseEntity<CurrencyRateDto> getCurrencyRate(
      @PathVariable("currency") String currency) {
    logger.info("GET /currencies/{}/rate", currency);
    CurrencyRateRule currencyRateRule = currencyRateDao.findByCurrency(currency);
    if (currencyRateRule == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    CurrencyRateDto dto = new CurrencyRateDto();
    dto.setCurrency(currency);
    dto.setRate(currencyRateRule.getRate());
    dto.setMore(currencyRateRule.isMore());
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  /**
   * Получение списка валют.
   */
  @GetMapping("/currencies")
  public ResponseEntity<List<CurrencyDto>> currencyList() {
    logger.info("GET /currencies");
    List<Currency> currencies = currencyDao.findAll();
    List<CurrencyDto> dto = currencies.stream().map(c -> {
      CurrencyDto cdto = new CurrencyDto();
      cdto.setCode(c.getCode());
      cdto.setId(c.getId());
      return cdto;
    }).collect(Collectors.toList());
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  /**
   * Добавление новой валюты.
   */
  @PostMapping(value = "/currencies", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Currency> createNewCurrency(@RequestBody CurrencyDto dto)
      throws JsonProcessingException {
    logger.info("POST /currencies {}", new ObjectMapper().writeValueAsString(dto));
    String code = dto.getCode();
    Currency currency = new Currency();
    currency.setCode(code);
    currency = currencyDao.save(currency);
    return new ResponseEntity<>(currency, HttpStatus.CREATED);
  }

  /**
   * Удаление валюты по ID.
   */
  @DeleteMapping("/currencies/{id}")
  public ResponseEntity<?> deleteCurrency(@PathVariable("id") Long id) {
    logger.info("DELETE /currencies/{}", id);
    Currency currency = currencyDao.getById(id);
    if (currency == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    currencyDao.delete(currency);
    return new ResponseEntity<>(HttpStatus.OK);
  }


}
