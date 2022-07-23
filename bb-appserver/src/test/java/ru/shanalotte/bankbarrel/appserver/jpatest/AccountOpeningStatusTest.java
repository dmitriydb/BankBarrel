package ru.shanalotte.bankbarrel.appserver.jpatest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shanalotte.bankbarrel.appserver.AbstractTestCase;
import ru.shanalotte.bankbarrel.appserver.domain.AccountOpeningStatus;
import ru.shanalotte.bankbarrel.appserver.repository.AccountOpeningStatusDao;

public class AccountOpeningStatusTest extends AbstractTestCase {

  private AccountOpeningStatus accountOpeningStatus;

  @BeforeEach
  public void refreshAccountOpeningStatus() {
    accountOpeningStatus = testUtils.generateRandomAccountOpeningStatus();
  }

  @Autowired
  private AccountOpeningStatusDao accountOpeningStatusDao;

  @Test
  public void accountOpeningStatusShouldHaveStatus() {
    accountOpeningStatus.setStatus(null);
    assertThrows(Throwable.class, () -> {
      accountOpeningStatusDao.save(accountOpeningStatus);
    });
  }

  @Test
  public void successfulTesting() {
    assertDoesNotThrow(() -> {
      accountOpeningStatusDao.save(accountOpeningStatus);
    });
  }
}
