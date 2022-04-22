package ru.shanalotte.bankbarrel.core;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import static ru.shanalotte.bankbarrel.core.CustomerCreationData.validName;
import static ru.shanalotte.bankbarrel.core.CustomerCreationData.validSurname;

@TestPropertySource("classpath:application.properties")
public class BankAccountCreationTest {

  @Test
  public void should_AssignOwnerToBankAccount_WhenCreated(){
    BankClient bankClient = new BankClient.Builder(validName, validSurname)
        .withEmail("abc@xyz.ema")
        .build();
    BankAccount bankAccount = new BankAccount.Builder()
        .withOwner(bankClient)
        .withType(BankAccountType.CHECKING)
        .withAdditionalType(BankAccountAdditionalType.INTEREST_BEARING)
        .build();
    assertThat(bankAccount.getOwner()).isEqualTo(bankClient);
  }

  @Test
  public void should_CreateRandomGUID_WhenBankAccountIsCreated(){
    BankClient bankClient = DummyService.createDummyCustomer();
    BankAccount bankAccount = new BankAccount.Builder()
        .withOwner(bankClient)
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
  public void bankAccount_shouldBeDollarCurrencyByDefault() {
    BankAccount account = DummyService.createDummyCheckingBankAccount();
    assertThat(account.getCurrency()).isEqualTo("USD");
  }

  @Test
  public void bankAccount_shouldHaveDescription() {
      BankAccount bankAccount = DummyService.createDummyCheckingBankAccount();
      assertThat(bankAccount.getDescription()).isNotBlank();
  }

  @Test
  public void customer_CanOpenRublesAccount() {
    BankAccount account = new BankAccount.Builder()
        .withOwner(DummyService.createDummyCustomer())
        .withType(BankAccountType.CHECKING)
        .withAdditionalType(BankAccountAdditionalType.PREMIUM)
        .withCurrency("RUB")
        .build();
    assertThat(account.getCurrency()).isEqualTo("RUB");
  }

}