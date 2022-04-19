package ru.shanalotte.bankbarrel.core;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class CheckingBankAccountCreationTest {



  @Test
  public void should_NotCreateCheckingBankAccount_WithSavingAccountAdditionalCategory(){
    Exception exception = assertThrows(Exception.class, () -> {
      BankAccount bankAccount = new BankAccount.Builder()
          .withOwner(DummyService.createDummyCustomer())
          .withType(BankAccountType.CHECKING)
          .withAdditionalType(BankAccountAdditionalType.SAVINGS_ONLY)
          .build();
    });
  }
}