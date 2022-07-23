package ru.shanalotte.bankbarrel.appserver.jpatest;


import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shanalotte.bankbarrel.appserver.AbstractTestCase;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyTransfer;
import ru.shanalotte.bankbarrel.appserver.repository.TransferDao;

public class MoneyTransferTest extends AbstractTestCase {

  @Autowired
  private TransferDao transferDao;

  private MoneyTransfer moneyTransfer;

  @BeforeEach
  public void refreshAccountOpening() {
    moneyTransfer = testUtils.generateRandomMoneyTransfer();
  }

  @Test
  public void shouldHaveToAccount() {
   moneyTransfer.setToAccount(null);
    assertThrows(Throwable.class, () -> {
      transferDao.save(moneyTransfer);
    });
  }

  @Test
  public void shouldHaveFromAccount() {
    moneyTransfer.setFromAccount(null);
    assertThrows(Throwable.class, () -> {
      transferDao.save(moneyTransfer);
    });
  }


  @Test
  public void shouldHaveTimestamp() {
    moneyTransfer.setTimestamp(null);
    assertThrows(Throwable.class, () -> {
      transferDao.save(moneyTransfer);
    });
  }

  @Test
  public void shouldHaveCurrency() {
    moneyTransfer.setCurrency(null);
    assertThrows(Throwable.class, () -> {
      transferDao.save(moneyTransfer);
    });
  }

  @Test
  public void shouldHaveOperationSource() {
    moneyTransfer.setOperationSource(null);
    assertThrows(Throwable.class, () -> {
      transferDao.save(moneyTransfer);
    });
  }

  @Test
  public void shouldNotHaveResult() {
    moneyTransfer.setResult(null);
    assertDoesNotThrow(() -> {
      transferDao.save(moneyTransfer);
    });
  }

  @Test
  public void transferValueCantBeNegative() {
    moneyTransfer.setAmount(new BigDecimal("-1"));
    assertThrows(Throwable.class, () -> {
      transferDao.save(moneyTransfer);
    });
  }


  @Test
  public void successfulSaving() {
    assertDoesNotThrow(() -> {
      transferDao.save(moneyTransfer);
    });
  }
}
