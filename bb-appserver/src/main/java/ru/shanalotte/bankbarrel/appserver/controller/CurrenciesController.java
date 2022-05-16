package ru.shanalotte.bankbarrel.appserver.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shanalotte.bankbarrel.appserver.domain.Currency;
import ru.shanalotte.bankbarrel.appserver.repository.CurrencyDao;
import ru.shanalotte.bankbarrel.appserver.repository.CurrencyRateDao;
import ru.shanalotte.bankbarrel.core.domain.CurrencyRateRule;
import ru.shanalotte.bankbarrel.core.dto.CurrencyDto;
import ru.shanalotte.bankbarrel.core.dto.CurrencyRateDto;

@RestController
public class CurrenciesController {

  private CurrencyDao currencyDao;
  private CurrencyRateDao currencyRateDao;

  public CurrenciesController(CurrencyDao currencyDao, CurrencyRateDao currencyRateDao) {
    this.currencyDao = currencyDao;
    this.currencyRateDao = currencyRateDao;
  }


  @PostMapping("/currencies/{currency}/rate")
  public ResponseEntity<CurrencyRateDto> createCurrencyRate(@PathVariable("currency") String currency,
                                                         @RequestBody CurrencyRateDto dto) {
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

  @PutMapping("/currencies/{currency}/rate")
  public ResponseEntity<CurrencyRateDto> updateCurrencyRate(@PathVariable("currency") String currency,
                                                         @RequestBody CurrencyRateDto dto) {
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


  @GetMapping("/currencies/{currency}/rate")
  public ResponseEntity<CurrencyRateDto> getCurrencyRate(@PathVariable("currency") String currency) {
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

  @GetMapping("/currencies")
  public ResponseEntity<List<CurrencyDto>> currencyList() {
    List<Currency> currencies = currencyDao.findAll();
    List<CurrencyDto> dto = currencies.stream().map(c -> {
      CurrencyDto cdto = new CurrencyDto();
      cdto.setCode(c.getCode());
      cdto.setId(c.getId());
      return cdto;
    }).collect(Collectors.toList());
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @PostMapping(value = "/currencies", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Currency> createNewCurrency(@RequestBody CurrencyDto dto) {
    String code = dto.getCode();
    Currency currency = new Currency();
    currency.setCode(code);
    currency = currencyDao.save(currency);
    return new ResponseEntity<>(currency, HttpStatus.CREATED);
  }

  @DeleteMapping("/currencies/{id}")
  public ResponseEntity<?> deleteCurrency(@PathVariable("id") Long id) {
    Currency currency = currencyDao.getById(id);
    if (currency == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    currencyDao.delete(currency);
    return new ResponseEntity<>(HttpStatus.OK);
  }


}
