package ru.shanalotte.bankbarrel.appserver.rest;

import java.math.BigDecimal;
import javax.xml.ws.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import static org.assertj.core.api.Assertions.assertThat;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.shanalotte.bankbarrel.appserver.AbstractRestTestCase;
import ru.shanalotte.bankbarrel.appserver.controller.CurrenciesController;
import ru.shanalotte.bankbarrel.appserver.domain.Currency;
import ru.shanalotte.bankbarrel.appserver.repository.CurrencyDao;
import ru.shanalotte.bankbarrel.core.dto.CurrencyDto;
import ru.shanalotte.bankbarrel.core.dto.CurrencyRateDto;

public class CurrenciesControllerTest extends AbstractRestTestCase {

  @Autowired
  private CurrenciesController currenciesController;

  @Autowired
  private CurrencyDao currencyDao;

  @Test
  public void createAndDeleteSomeCurrency() throws JsonProcessingException {
    CurrencyDto currencyDto = dtoUtils.generateCurrencyDto();
    currencyDto = currenciesController.createNewCurrency(currencyDto).getBody();
    assertThat(currencyDto.getId()).isNotNull();
    currenciesController.deleteCurrency(currencyDto.getId());
  }

  @Test
  public void creatingAndUpdatingSomeCurrencyRate() throws JsonProcessingException {
    CurrencyDto currencyDto = dtoUtils.generateCurrencyDto();
    currencyDto = currenciesController.createNewCurrency(currencyDto).getBody();
    CurrencyRateDto currencyRateDto = dtoUtils.generateCurrencyRateDto(currencyDto.getCode());
    currenciesController.createCurrencyRate(currencyDto.getCode(), currencyRateDto);
    CurrencyRateDto dtoAfter = currenciesController.getCurrencyRate(currencyDto.getCode()).getBody();
    assertThat(dtoAfter.getRate()).isEqualByComparingTo(currencyRateDto.getRate());
    assertThat(dtoAfter.isMore()).isEqualTo(currencyRateDto.isMore());
    currencyRateDto.setMore(!currencyRateDto.isMore());
    currencyRateDto.setRate(currencyRateDto.getRate().add(BigDecimal.valueOf(100)));
    currenciesController.updateCurrencyRate(currencyDto.getCode(), currencyRateDto);
    dtoAfter = currenciesController.getCurrencyRate(currencyDto.getCode()).getBody();
    assertThat(dtoAfter.getRate()).isEqualByComparingTo(currencyRateDto.getRate());
    assertThat(dtoAfter.isMore()).isEqualTo(currencyRateDto.isMore());
  }

  @Test
  public void deleteCurrencyByNullIs404() {
    ResponseEntity<?> response = currenciesController.deleteCurrency(null);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void updateCurrencyByNullIs404() throws JsonProcessingException {
    ResponseEntity<?> response = currenciesController.updateCurrencyRate(null, null);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void updateCurrencyByMissingCode404() throws JsonProcessingException {
    ResponseEntity<?> response = currenciesController.updateCurrencyRate("xyz", null);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void deleteCurrencyByMissingIdIs404() {
    ResponseEntity<?> response = currenciesController.deleteCurrency(-1L);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void tryFindUSD() {
    Currency currency = currencyDao.findByCode("USD");
    System.out.println(currency);
  }

  @Test
  public void findCurrencyCodeNullIs404() {
    ResponseEntity<?> response = currenciesController.getCurrencyRate(null);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void findCurrencyByMissingCodeIs404() {
    ResponseEntity<?> response = currenciesController.getCurrencyRate("xxdws");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

}
