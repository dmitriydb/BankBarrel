package ru.shanalotte.bankbarrel.appserver.jpatest;

import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shanalotte.bankbarrel.appserver.AbstractTestCase;
import ru.shanalotte.bankbarrel.appserver.domain.BankAccountAdditionalTypeEntity;
import ru.shanalotte.bankbarrel.appserver.domain.BankAccountTypeEntity;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountAdditionalTypeDao;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountTypeDao;

public class AccountTypeTest extends AbstractTestCase {

  private BankAccountTypeEntity accountType;

  @Autowired
  private BankAccountTypeDao bankAccountTypeDao;

  @Autowired
  private BankAccountAdditionalTypeDao bankAccountAdditionalTypeDao;

  @BeforeEach
  public void refresh() {
    accountType = testUtils.generateRandomAccountType();
  }


  @Test
  public void shouldHaveCode() {
    accountType.setCode(null);
    assertThrows(Throwable.class, () -> {
      bankAccountTypeDao.save(accountType);
    });
  }

  @Test
  public void shouldHaveDescription() {
    accountType.setDescription(null);
    assertThrows(Throwable.class, () -> {
      bankAccountTypeDao.save(accountType);
    });
  }

  @Test
  public void successfulSaving() {
    assertDoesNotThrow(() -> {
      bankAccountTypeDao.save(accountType);
    });
  }

  @Test
  public void subTypesPersistingWithOwnerType() {
    BankAccountTypeEntity owner = testUtils.generateRandomAccountType();
    BankAccountAdditionalTypeEntity subtype1 = testUtils.generateRandomAdditionalAccountType();
    BankAccountAdditionalTypeEntity subtype2 = testUtils.generateRandomAdditionalAccountType();
    BankAccountAdditionalTypeEntity subtype3 = testUtils.generateRandomAdditionalAccountType();
    owner.addSubType(subtype1);
    owner.addSubType(subtype2);
    owner.addSubType(subtype3);
    bankAccountTypeDao.save(owner);
    assertThat(subtype1.getId()).isNotNull();
    assertThat(subtype2.getId()).isNotNull();
    assertThat(subtype3.getId()).isNotNull();
  }

  @Test
  public void subTypesRemovingWithOwnerType() {
    BankAccountTypeEntity owner = testUtils.generateRandomAccountType();
    BankAccountAdditionalTypeEntity subtype1 = testUtils.generateRandomAdditionalAccountType();
    BankAccountAdditionalTypeEntity subtype2 = testUtils.generateRandomAdditionalAccountType();
    BankAccountAdditionalTypeEntity subtype3 = testUtils.generateRandomAdditionalAccountType();
    owner.addSubType(subtype1);
    owner.addSubType(subtype2);
    owner.addSubType(subtype3);
    bankAccountTypeDao.save(owner);
    List<Long> subTypesIds = Arrays.asList(subtype1.getId(), subtype2.getId(), subtype3.getId());
    for (Long id : subTypesIds) {
      assertThat(bankAccountAdditionalTypeDao.findById(id)).isNotEmpty();
    }
    bankAccountTypeDao.delete(owner);
    for (Long id : subTypesIds) {
      assertThat(bankAccountAdditionalTypeDao.findById(id)).isEmpty();
    }
  }
}
