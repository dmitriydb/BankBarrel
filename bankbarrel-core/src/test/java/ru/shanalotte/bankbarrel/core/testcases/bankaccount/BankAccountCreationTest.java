package ru.shanalotte.bankbarrel.core.testcases.bankaccount;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import static ru.shanalotte.bankbarrel.core.service.DummyTestingEntitiesConstants.VALID_BANK_CLIENT_NAME;
import static ru.shanalotte.bankbarrel.core.service.DummyTestingEntitiesConstants.VALID_BANK_CLIENT_SURNAME;
import ru.shanalotte.bankbarrel.core.service.DummyTestingEntitiesProvider;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.BankAccountAdditionalType;
import ru.shanalotte.bankbarrel.core.domain.BankAccountType;
import ru.shanalotte.bankbarrel.core.domain.BankClient;

@TestPropertySource("classpath:application.properties")
public class BankAccountCreationTest {

  @Test
  public void should_AssignOwnerToBankAccount_WhenCreated(){
    BankClient bankClient = DummyTestingEntitiesProvider.createValidBankClientDummy();
    BankAccount bankAccount = new BankAccount.Builder()
        .withOwner(bankClient)
        .withType(BankAccountType.CHECKING)
        .withAdditionalType(BankAccountAdditionalType.INTEREST_BEARING)
        .build();
    assertThat(bankAccount.getOwner()).isEqualTo(bankClient);
  }

  @Test
  public void should_GenerateBankAccountIdentifier_WhenBankAccountIsCreated(){
    BankClient bankClient = DummyTestingEntitiesProvider.createValidBankClientDummy();
    BankAccount bankAccount = new BankAccount.Builder()
        .withOwner(bankClient)
        .withType(BankAccountType.CHECKING)
        .withAdditionalType(BankAccountAdditionalType.INTEREST_BEARING)
        .build();
    assertThat(bankAccount.getIdentifier()).isNotBlank();
  }

  @Test
  public void checkingBankAccount_ShouldHaveTheCheckingBankAccountType(){
    BankAccount bankAccount = new BankAccount.Builder()
        .withOwner(DummyTestingEntitiesProvider.createValidBankClientDummy())
        .withType(BankAccountType.CHECKING)
        .withAdditionalType(BankAccountAdditionalType.INTEREST_BEARING)
        .build();
    assertThat(bankAccount.getBankAccountType()).isEqualTo("CHECKING");
  }

  @Test
  public void savingBankAccount_ShouldHaveTheSavingBankAccountType(){
    BankAccount bankAccount = new BankAccount.Builder()
        .withOwner(DummyTestingEntitiesProvider.createValidBankClientDummy())
        .withType(BankAccountType.SAVING)
        .withAdditionalType(BankAccountAdditionalType.SAVINGS_ONLY)
        .build();
    assertThat(bankAccount.getBankAccountType()).isEqualTo("SAVING");
  }

  @Test
  public void differentBankAccountTypesBuildingTest(){
    for (BankAccountType type : BankAccountType.values()) {
      for (BankAccountAdditionalType additionalType : type.getAdditionalTypes()) {
        BankAccount account = new BankAccount.Builder()
            .withOwner(DummyTestingEntitiesProvider.createValidBankClientDummy())
            .withType(type)
            .withAdditionalType(additionalType)
            .build();
        assertThat(account.getBankAccountType()).isEqualTo(type.name());
        assertThat(account.getAdditionalType()).isEqualTo(additionalType.name());
      }
    }
  }

  @Test
  public void bankAccount_shouldBeDollarCurrencyByDefault() {
    BankAccount account = DummyTestingEntitiesProvider.createCheckingBankAccountDummy();
    assertThat(account.getCurrency()).isEqualTo("USD");
  }

  @Test
  public void bankAccount_shouldHaveDescription() {
      BankAccount bankAccount = DummyTestingEntitiesProvider.createCheckingBankAccountDummy();
      assertThat(bankAccount.getDescription()).isNotBlank();
  }

  @Test
  public void customer_shouldAllowOpeningBankAccountInCurrencyOtherThanUSD() {
    BankAccount account = new BankAccount.Builder()
        .withOwner(DummyTestingEntitiesProvider.createValidBankClientDummy())
        .withType(BankAccountType.CHECKING)
        .withAdditionalType(BankAccountAdditionalType.PREMIUM)
        .withCurrency("RUB")
        .build();
    assertThat(account.getCurrency()).isEqualTo("RUB");
  }

}