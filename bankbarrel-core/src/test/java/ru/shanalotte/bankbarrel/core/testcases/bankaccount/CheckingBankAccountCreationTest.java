package ru.shanalotte.bankbarrel.core.testcases.bankaccount;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import ru.shanalotte.bankbarrel.core.service.DummyTestingEntitiesProvider;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.BankAccountAdditionalType;
import ru.shanalotte.bankbarrel.core.domain.BankAccountType;


public class CheckingBankAccountCreationTest {

  @Test
  public void should_NotCreateCheckingBankAccount_WithSavingAccountAdditionalCategory(){
   assertThrows(Exception.class, () -> {
     new BankAccount.Builder()
          .withOwner(DummyTestingEntitiesProvider.createValidBankClientDummy())
          .withType(BankAccountType.CHECKING)
          .withAdditionalType(BankAccountAdditionalType.SAVINGS_ONLY)
          .build();
    });
  }
}