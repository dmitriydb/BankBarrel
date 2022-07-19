package ru.shanalotte.bankbarrel.appserver.jpatest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shanalotte.bankbarrel.appserver.AbstractTestCase;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyWithdrawStatus;
import ru.shanalotte.bankbarrel.appserver.repository.MoneyDepositStatusDao;
import ru.shanalotte.bankbarrel.appserver.repository.MoneyWithdrawStatusDao;

public class WithdrawStatusTest extends AbstractTestCase {

  private MoneyWithdrawStatus moneyWithdrawStatus;

  @BeforeEach
  public void refreshAccountOpeningStatus() {
    moneyWithdrawStatus = testUtils.generateRandomMoneyWithdrawStatus();
  }

  @Autowired
  private MoneyWithdrawStatusDao moneyWithdrawStatusDao;

  @Test
  public void shouldHaveStatus() {
    moneyWithdrawStatus.setStatus(null);
    assertThrows(Throwable.class, () -> {
      moneyWithdrawStatusDao.save(moneyWithdrawStatus);
    });
  }

  @Test
  public void successfulTesting() {
    assertDoesNotThrow(() -> {
      moneyWithdrawStatusDao.save(moneyWithdrawStatus);
    });
  }
}
