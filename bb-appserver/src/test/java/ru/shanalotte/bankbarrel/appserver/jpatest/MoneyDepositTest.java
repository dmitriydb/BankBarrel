package ru.shanalotte.bankbarrel.appserver.jpatest;


import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shanalotte.bankbarrel.appserver.AbstractTestCase;
import ru.shanalotte.bankbarrel.appserver.domain.AccountOpening;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyDeposit;
import ru.shanalotte.bankbarrel.appserver.repository.AccountOpeningRepository;
import ru.shanalotte.bankbarrel.appserver.repository.DepositDao;

public class MoneyDepositTest extends AbstractTestCase {

  @Autowired
  private DepositDao depositDao;

  private MoneyDeposit moneyDeposit;

  @BeforeEach
  public void refreshAccountOpening() {
    moneyDeposit = testUtils.generateRandomMoneyDeposit();
  }

  @Test
  public void shouldHaveAccount() {
   moneyDeposit.setAccount(null);
    assertThrows(Throwable.class, () -> {
      depositDao.save(moneyDeposit);
    });
  }

  @Test
  public void shouldHaveTimestamp() {
    moneyDeposit.setTimestamp(null);
    assertThrows(Throwable.class, () -> {
      depositDao.save(moneyDeposit);
    });
  }

  @Test
  public void shouldHaveCurrency() {
    moneyDeposit.setCurrency(null);
    assertThrows(Throwable.class, () -> {
      depositDao.save(moneyDeposit);
    });
  }

  @Test
  public void shouldHaveOperationSource() {
    moneyDeposit.setOperationSource(null);
    assertThrows(Throwable.class, () -> {
      depositDao.save(moneyDeposit);
    });
  }

  @Test
  public void shouldNotHaveResult() {
    moneyDeposit.setResult(null);
    assertDoesNotThrow(() -> {
      depositDao.save(moneyDeposit);
    });
  }

  @Test
  public void depositValueCantBeNegative() {
    moneyDeposit.setAmount(new BigDecimal("-1"));
    assertThrows(Throwable.class, () -> {
      depositDao.save(moneyDeposit);
    });
  }


  @Test
  public void successfulSaving() {
    assertDoesNotThrow(() -> {
      depositDao.save(moneyDeposit);
    });
  }
}
