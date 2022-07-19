package ru.shanalotte.bankbarrel.appserver.jpatest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shanalotte.bankbarrel.appserver.TestUtils;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyWithdrawHistory;
import ru.shanalotte.bankbarrel.appserver.repository.MoneyWithdrawHistoryDao;

@SpringBootTest
public class MoneyWithdrawHistoryTest {

  @Autowired
  private TestUtils testUtils;

  private MoneyWithdrawHistory moneyWithdrawHistory;

  @Autowired
  private MoneyWithdrawHistoryDao moneyWithdrawHistoryDao;

  @BeforeEach
  public void refreshAccountOpeningHistory() {
    moneyWithdrawHistory = testUtils.generateRandomMoneyWithdrawHistory();
  }

  @Test
  public void shouldHaveMoneyWithdraw() {
    moneyWithdrawHistory.setMoneyWithdraw(null);
    assertThrows(Throwable.class, () -> {
      moneyWithdrawHistoryDao.save(moneyWithdrawHistory);
    });
  }

  @Test
  public void shouldHaveTimestamp() {
    moneyWithdrawHistory.setTimestamp(null);
    assertThrows(Throwable.class, () -> {
      moneyWithdrawHistoryDao.save(moneyWithdrawHistory);
    });
  }

  @Test
  public void shouldHaveStatus() {
    moneyWithdrawHistory.setStatus(null);
    assertThrows(Throwable.class, () -> {
      moneyWithdrawHistoryDao.save(moneyWithdrawHistory);
    });
  }

  @Test
  public void successfulSaving() {
    assertDoesNotThrow(() -> {
      moneyWithdrawHistoryDao.save(moneyWithdrawHistory);
    });
  }

}
