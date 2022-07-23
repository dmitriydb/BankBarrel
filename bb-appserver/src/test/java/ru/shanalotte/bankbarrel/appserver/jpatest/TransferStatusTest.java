package ru.shanalotte.bankbarrel.appserver.jpatest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shanalotte.bankbarrel.appserver.AbstractTestCase;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyTransferStatus;
import ru.shanalotte.bankbarrel.appserver.repository.MoneyTransferStatusDao;
import ru.shanalotte.bankbarrel.appserver.repository.MoneyWithdrawStatusDao;

public class TransferStatusTest extends AbstractTestCase {

  private MoneyTransferStatus moneyTransferStatus;

  @BeforeEach
  public void refreshAccountOpeningStatus() {
    moneyTransferStatus = testUtils.generateRandomMoneyTransferStatus();
  }

  @Autowired
  private MoneyTransferStatusDao moneyTransferStatusDao;

  @Test
  public void shouldHaveStatus() {
    moneyTransferStatus.setStatus(null);
    assertThrows(Throwable.class, () -> {
      moneyTransferStatusDao.save(moneyTransferStatus);
    });
  }

  @Test
  public void successfulTesting() {
    assertDoesNotThrow(() -> {
      moneyTransferStatusDao.save(moneyTransferStatus);
    });
  }
}
