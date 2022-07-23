package ru.shanalotte.bankbarrel.appserver.jpatest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shanalotte.bankbarrel.appserver.TestUtils;
import ru.shanalotte.bankbarrel.appserver.domain.AccountOpening;
import ru.shanalotte.bankbarrel.appserver.domain.AccountOpeningHistory;
import ru.shanalotte.bankbarrel.appserver.repository.AccountOpeningHistoryDao;

@SpringBootTest
public class AccountOpeningHistoryTest {

  @Autowired
  private TestUtils testUtils;

  private AccountOpeningHistory accountOpeningHistory;

  @Autowired
  private AccountOpeningHistoryDao accountOpeningHistoryDao;

  @BeforeEach
  public void refreshAccountOpeningHistory() {
    accountOpeningHistory = testUtils.generateRandomAccountOpeningHistory();
  }

  @Test
  public void accountOpeningHistoryShouldHaveOpening() {
    accountOpeningHistory.setAccountOpening(null);
    assertThrows(Throwable.class, () -> {
      accountOpeningHistoryDao.save(accountOpeningHistory);
    });
  }

  @Test
  public void accountOpeningHistoryShouldHaveStatus() {
    accountOpeningHistory.setStatus(null);
    assertThrows(Throwable.class, () -> {
      accountOpeningHistoryDao.save(accountOpeningHistory);
    });
  }

  @Test
  public void accountOpeningHistoryShouldHaveTimestamp() {
    accountOpeningHistory.setTimestamp(null);
    assertThrows(Throwable.class, () -> {
      accountOpeningHistoryDao.save(accountOpeningHistory);
    });
  }

  @Test
  public void successfulSaving() {
    assertDoesNotThrow(() -> {
      accountOpeningHistoryDao.save(accountOpeningHistory);
    });
  }

}
