package ru.shanalotte.bankbarrel.appserver.jpatest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shanalotte.bankbarrel.appserver.AbstractTestCase;
import ru.shanalotte.bankbarrel.appserver.domain.BankAccountAdditionalTypeEntity;
import ru.shanalotte.bankbarrel.appserver.domain.Currency;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountAdditionalTypeDao;
import ru.shanalotte.bankbarrel.appserver.repository.CurrencyDao;

public class CurrencyTest extends AbstractTestCase {

  private Currency currency;

  @Autowired
  private CurrencyDao currencyDao;

  @BeforeEach
  public void refresh() {
    currency = testUtils.generateRandomCurrency();
  }

  @Test
  public void shouldHaveCode() {
    currency.setCode(null);
    assertThrows(Throwable.class, () -> {
      currencyDao.save(currency);
    });
  }

  @Test
  public void successfulSaving() {
    assertDoesNotThrow(() -> {
      currencyDao.save(currency);
    });
  }
}
