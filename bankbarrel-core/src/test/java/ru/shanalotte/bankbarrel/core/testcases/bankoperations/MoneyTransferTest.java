package ru.shanalotte.bankbarrel.core.testcases.bankoperations;

import java.math.BigDecimal;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import ru.shanalotte.bankbarrel.core.service.DummyTestingEntitiesProvider;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRateForRequestedCurrency;
import ru.shanalotte.bankbarrel.core.service.SimpleBankService;

public class MoneyTransferTest {

  @Test
  public void should_ThrowExceptionIfInsufficientFunds_WhenWithdrawMoneyFromBankAccount() {
    BankAccount bankAccount = DummyTestingEntitiesProvider.createCheckingBankAccountDummy();
    SimpleBankService bankService = DummyTestingEntitiesProvider.createTestingSimpleBankService();
    assertThrows(InsufficientFundsException.class, () -> {
      bankService.withdraw(bankAccount, new MonetaryAmount(1, "USD"));
    });
  }

  @Test
  public void should_ThrowExceptionIfInsufficientFunds_WhenWithdrawMoneyFromBankAccount_evenIfDiffersOnlyALittle()
      throws UnknownCurrencyRateForRequestedCurrency {
    BankAccount bankAccount = DummyTestingEntitiesProvider.createCheckingBankAccountDummy();
    SimpleBankService bankService = DummyTestingEntitiesProvider.createTestingSimpleBankService();
    bankService.deposit(bankAccount, new MonetaryAmount(0.12320, "USD"));
    assertThrows(InsufficientFundsException.class, () -> {
      bankService.withdraw(bankAccount, new MonetaryAmount(0.12321, "USD"));
    });
  }

  @Test
  public void shouldNotThrowExceptionIfSufficientFundsWhenWithdraw() throws UnknownCurrencyRateForRequestedCurrency {
    BankAccount bankAccount = DummyTestingEntitiesProvider.createCheckingBankAccountDummy();
    SimpleBankService bankService = DummyTestingEntitiesProvider.createTestingSimpleBankService();
    bankService.deposit(bankAccount, new MonetaryAmount(0.12321, "USD"));
    assertDoesNotThrow(() -> {
      bankService.withdraw(bankAccount, new MonetaryAmount(0.12321, "USD"));
    });
  }

  @Test
  public void shouldThrowException_WhenInsufficientAccountForTransfer(){
    BankAccount bankAccount = DummyTestingEntitiesProvider.createCheckingBankAccountDummy();
    BankAccount bankAccount2 = DummyTestingEntitiesProvider.createCheckingBankAccountDummy();
    SimpleBankService bankService = DummyTestingEntitiesProvider.createTestingSimpleBankService();
    assertThrows(InsufficientFundsException.class, () -> {
      bankService.transfer(bankAccount, bankAccount2, new MonetaryAmount(1, "USD"));
    });
  }

  @Test
  public void successfulTransferSimulation() throws InsufficientFundsException, UnknownCurrencyRateForRequestedCurrency {
    BankAccount bankAccount = DummyTestingEntitiesProvider.createCheckingBankAccountDummy();
    BankAccount bankAccount2 = DummyTestingEntitiesProvider.createCheckingBankAccountDummy();
    SimpleBankService bankService = DummyTestingEntitiesProvider.createTestingSimpleBankService();
    bankService.deposit(bankAccount, new MonetaryAmount(0.66, "USD"));
    assertThat(bankAccount.toMonetaryAmount().getValue()).isEqualByComparingTo(new BigDecimal("0.66"));
    assertThat(bankAccount2.toMonetaryAmount().getValue()).isEqualByComparingTo(new BigDecimal("0"));
    bankService.transfer(bankAccount, bankAccount2, new MonetaryAmount(0.66, "USD"));
    assertThat(bankAccount2.toMonetaryAmount().getValue()).isEqualByComparingTo(new BigDecimal("0.66"));
    assertThat(bankAccount.toMonetaryAmount().getValue()).isEqualByComparingTo(new BigDecimal("0"));
  }
}
