package ru.shanalotte.bankbarrel.appserver.jpatest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shanalotte.bankbarrel.appserver.AbstractTestCase;
import ru.shanalotte.bankbarrel.appserver.domain.Currency;
import ru.shanalotte.bankbarrel.appserver.domain.OperationSource;
import ru.shanalotte.bankbarrel.appserver.repository.CurrencyDao;
import ru.shanalotte.bankbarrel.appserver.repository.OperationSourceDao;

public class OperationSourceTest extends AbstractTestCase {

  private OperationSource operationSource;

  @Autowired
  private OperationSourceDao operationSourceDao;

  @BeforeEach
  public void refresh() {
    operationSource = testUtils.generateRandomOperationSource();
  }

  @Test
  public void shouldHaveName() {
    operationSource.setName(null);
    assertThrows(Throwable.class, () -> {
      operationSourceDao.save(operationSource);
    });
  }

  @Test
  public void successfulSaving() {
    assertDoesNotThrow(() -> {
      operationSourceDao.save(operationSource);
    });
  }
}
