package ru.shanalotte.bankbarrel.appserver.jpatest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shanalotte.bankbarrel.appserver.TestUtils;
import ru.shanalotte.bankbarrel.appserver.domain.AccountOpeningHistory;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyDepositHistory;
import ru.shanalotte.bankbarrel.appserver.repository.AccountOpeningHistoryDao;
import ru.shanalotte.bankbarrel.appserver.repository.MoneyDepositHistoryDao;

@SpringBootTest
public class MoneyDepositHistoryTest {

  @Autowired
  private TestUtils testUtils;

  private MoneyDepositHistory moneyDepositHistory;

  @Autowired
  private MoneyDepositHistoryDao moneyDepositHistoryDao;

  @BeforeEach
  public void refreshAccountOpeningHistory() {
    moneyDepositHistory = testUtils.generateRandomMoneyDepositHistory();
  }

  @Test
  public void shouldHaveMoneyDeposit() {
    moneyDepositHistory.setMoneyDeposit(null);
    assertThrows(Throwable.class, () -> {
      moneyDepositHistoryDao.save(moneyDepositHistory);
    });
  }

  @Test
  public void shouldHaveTimestamp() {
    moneyDepositHistory.setTimestamp(null);
    assertThrows(Throwable.class, () -> {
      moneyDepositHistoryDao.save(moneyDepositHistory);
    });
  }

  @Test
  public void shouldHaveStatus() {
    moneyDepositHistory.setStatus(null);
    assertThrows(Throwable.class, () -> {
      moneyDepositHistoryDao.save(moneyDepositHistory);
    });
  }

  @Test
  public void successfulSaving() {
    assertDoesNotThrow(() -> {
      moneyDepositHistoryDao.save(moneyDepositHistory);
    });
  }

}
