package ru.shanalotte.bankbarrel.appserver.jpatest;

import net.bytebuddy.implementation.bytecode.Throw;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shanalotte.bankbarrel.appserver.AbstractTestCase;
import ru.shanalotte.bankbarrel.appserver.domain.BankAccountAdditionalTypeEntity;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountAdditionalTypeDao;

public class AccountAdditionalTypeTest extends AbstractTestCase {

  private BankAccountAdditionalTypeEntity accountAdditionalType;

  @Autowired
  private BankAccountAdditionalTypeDao bankAccountAdditionalTypeDao;

  @BeforeEach
  public void refresh() {
    accountAdditionalType = testUtils.generateRandomAdditionalAccountType();
  }

  @Test
  public void shouldHaveOwnerType() {
    accountAdditionalType.setOwnerType(null);
    assertThrows(Throwable.class, () -> {
      bankAccountAdditionalTypeDao.save(accountAdditionalType);
    });
  }

  @Test
  public void shouldHaveCode() {
    accountAdditionalType.setCode(null);
    assertThrows(Throwable.class, () -> {
      bankAccountAdditionalTypeDao.save(accountAdditionalType);
    });
  }

  @Test
  public void shouldHaveDescription() {
    accountAdditionalType.setDescription(null);
    assertThrows(Throwable.class, () -> {
      bankAccountAdditionalTypeDao.save(accountAdditionalType);
    });
  }

  @Test
  public void successfulSaving() {
    assertDoesNotThrow(() -> {
      bankAccountAdditionalTypeDao.save(accountAdditionalType);
    });
  }
}
