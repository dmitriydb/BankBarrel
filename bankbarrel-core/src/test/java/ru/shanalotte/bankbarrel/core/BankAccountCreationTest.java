package ru.shanalotte.bankbarrel.core;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import static ru.shanalotte.bankbarrel.core.CustomerCreationData.validName;
import static ru.shanalotte.bankbarrel.core.CustomerCreationData.validSurname;

public class BankAccountCreationTest {

  @Test
  public void should_AssignOwnerToBankAccount_WhenCreated(){
    Customer customer = new Customer.Builder(validName, validSurname)
        .withEmail("abc@xyz.ema")
        .build();
    BankAccount bankAccount = new BankAccount.Builder()
        .withOwner(customer)
        .withType(BankAccountType.CHECKING)
        .withAdditionalType(BankAccountAdditionalType.INTEREST_BEARING)
        .build();
    assertThat(bankAccount.getOwner()).isEqualTo(customer);
  }

  @Test
  public void should_CreateRandomGUID_WhenBankAccountIsCreated(){
    Customer customer = DummyService.createDummyCustomer();
    BankAccount bankAccount = new BankAccount.Builder()
        .withOwner(customer)
        .withType(BankAccountType.CHECKING)
        .withAdditionalType(BankAccountAdditionalType.INTEREST_BEARING)
        .build();
    assertThat(bankAccount.getIdentifier()).isNotBlank();
  }

  @Test
  public void checkingBankAccount_ShouldHasTheCheckingBankAccountType(){
    BankAccount bankAccount = new BankAccount.Builder()
        .withOwner(DummyService.createDummyCustomer())
        .withType(BankAccountType.CHECKING)
        .withAdditionalType(BankAccountAdditionalType.INTEREST_BEARING)
        .build();
    assertThat(bankAccount.getBankAccountType()).isEqualTo(BankAccountType.CHECKING);
  }

  @Test
  public void savingBankAccount_ShouldHasTheSavingBankAccountType(){
    BankAccount bankAccount = new BankAccount.Builder()
        .withOwner(DummyService.createDummyCustomer())
        .withType(BankAccountType.SAVING)
        .withAdditionalType(BankAccountAdditionalType.SAVINGS_ONLY)
        .build();
    assertThat(bankAccount.getBankAccountType()).isEqualTo(BankAccountType.SAVING);
  }

  @Test
  public void bankAccountBuilderTest(){
    for (BankAccountType type : BankAccountType.values()) {
      for (BankAccountAdditionalType additionalType : type.getAdditionalTypes()) {
        BankAccount account = new BankAccount.Builder()
            .withOwner(DummyService.createDummyCustomer())
            .withType(type)
            .withAdditionalType(additionalType)
            .build();
        assertThat(account.getBankAccountType()).isEqualTo(type);
        assertThat(account.getAdditionalType()).isEqualTo(additionalType);
      }
    }
  }

  @Test
  public void bankAccount_shouldHaveDescription() {
      BankAccount bankAccount = DummyService.createDummyCheckingBankAccount();
      assertThat(bankAccount.getDescription()).isNotBlank();
  }
}