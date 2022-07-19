package ru.shanalotte.bankbarrel.appserver.jpatest;


import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shanalotte.bankbarrel.appserver.AbstractTestCase;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyWithdraw;
import ru.shanalotte.bankbarrel.appserver.repository.DepositDao;
import ru.shanalotte.bankbarrel.appserver.repository.WithdrawDao;

public class MoneyWithdrawTest extends AbstractTestCase {

  @Autowired
  private WithdrawDao withdrawDao;

  private MoneyWithdraw moneyWithdraw;

  @BeforeEach
  public void refreshAccountOpening() {
    moneyWithdraw = testUtils.generateRandomMoneyWithdraw();
  }

  @Test
  public void shouldHaveAccount() {
   moneyWithdraw.setAccount(null);
    assertThrows(Throwable.class, () -> {
      withdrawDao.save(moneyWithdraw);
    });
  }

  @Test
  public void shouldHaveTimestamp() {
    moneyWithdraw.setTimestamp(null);
    assertThrows(Throwable.class, () -> {
      withdrawDao.save(moneyWithdraw);
    });
  }

  @Test
  public void shouldHaveCurrency() {
    moneyWithdraw.setCurrency(null);
    assertThrows(Throwable.class, () -> {
      withdrawDao.save(moneyWithdraw);
    });
  }

  @Test
  public void shouldHaveOperationSource() {
    moneyWithdraw.setOperationSource(null);
    assertThrows(Throwable.class, () -> {
      withdrawDao.save(moneyWithdraw);
    });
  }

  @Test
  public void shouldNotHaveResult() {
    moneyWithdraw.setResult(null);
    assertDoesNotThrow(() -> {
      withdrawDao.save(moneyWithdraw);
    });
  }

  @Test
  public void withdrawValueCantBeNegative() {
    moneyWithdraw.setAmount(new BigDecimal("-1"));
    assertThrows(Throwable.class, () -> {
      withdrawDao.save(moneyWithdraw);
    });
  }


  @Test
  public void successfulSaving() {
    assertDoesNotThrow(() -> {
      withdrawDao.save(moneyWithdraw);
    });
  }
}
