package ru.shanalotte.bankbarrel.appserver.jpatest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shanalotte.bankbarrel.appserver.AbstractTestCase;
import ru.shanalotte.bankbarrel.appserver.domain.AccountOpeningStatus;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyDepositStatus;
import ru.shanalotte.bankbarrel.appserver.repository.AccountOpeningStatusDao;
import ru.shanalotte.bankbarrel.appserver.repository.MoneyDepositStatusDao;

public class DepositStatusTest extends AbstractTestCase {

  private MoneyDepositStatus moneyDepositStatus;

  @BeforeEach
  public void refreshAccountOpeningStatus() {
    moneyDepositStatus = testUtils.generateRandomMoneyDepositStatus();
  }

  @Autowired
  private MoneyDepositStatusDao moneyDepositStatusDao;

  @Test
  public void shouldHaveStatus() {
    moneyDepositStatus.setStatus(null);
    assertThrows(Throwable.class, () -> {
      moneyDepositStatusDao.save(moneyDepositStatus);
    });
  }

  @Test
  public void successfulTesting() {
    assertDoesNotThrow(() -> {
      moneyDepositStatusDao.save(moneyDepositStatus);
    });
  }
}
