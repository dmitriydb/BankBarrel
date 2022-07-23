package ru.shanalotte.bankbarrel.appserver.jpatest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shanalotte.bankbarrel.appserver.TestUtils;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyTransferHistory;
import ru.shanalotte.bankbarrel.appserver.repository.MoneyTransferHistoryDao;

@SpringBootTest
public class MoneyTransferHistoryTest {

  @Autowired
  private TestUtils testUtils;

  private MoneyTransferHistory moneyTransferHistory;

  @Autowired
  private MoneyTransferHistoryDao moneyTransferHistoryDao;

  @BeforeEach
  public void refreshAccountOpeningHistory() {
    moneyTransferHistory = testUtils.generateRandomMoneyTransferHistory();
  }

  @Test
  public void shouldHaveMoneyTransfer() {
    moneyTransferHistory.setMoneyTransfer(null);
    assertThrows(Throwable.class, () -> {
      moneyTransferHistoryDao.save(moneyTransferHistory);
    });
  }

  @Test
  public void shouldHaveTimestamp() {
    moneyTransferHistory.setTimestamp(null);
    assertThrows(Throwable.class, () -> {
      moneyTransferHistoryDao.save(moneyTransferHistory);
    });
  }

  @Test
  public void shouldHaveStatus() {
    moneyTransferHistory.setStatus(null);
    assertThrows(Throwable.class, () -> {
      moneyTransferHistoryDao.save(moneyTransferHistory);
    });
  }

  @Test
  public void successfulSaving() {
    assertDoesNotThrow(() -> {
      moneyTransferHistoryDao.save(moneyTransferHistory);
    });
  }

}
