package ru.shanalotte.bankbarrel.core;

import java.math.BigDecimal;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;
import ru.shanalotte.bankbarrel.core.service.BankService;

public class MoneyTransferTest {

  @Test
  public void shouldThrowExceptionIfInsufficientFunds() {
    BankAccount bankAccount = DummyService.createDummyCheckingBankAccount();
    BankService bankService = DummyService.dummyBankService();
    assertThrows(InsufficientFundsException.class, () -> {
      bankService.withdraw(bankAccount, new MonetaryAmount(1, "USD"));
    });
  }

  @Test
  public void shouldThrowExceptionIfInsufficientFunds2() throws UnknownCurrencyRate {
    BankAccount bankAccount = DummyService.createDummyCheckingBankAccount();
    BankService bankService = DummyService.dummyBankService();
    bankService.deposit(bankAccount, new MonetaryAmount(0.12320, "USD"));
    assertThrows(InsufficientFundsException.class, () -> {
      bankService.withdraw(bankAccount, new MonetaryAmount(0.12321, "USD"));
    });
  }

  @Test
  public void shouldNotThrowExceptionIfSufficientFunds() throws UnknownCurrencyRate {
    BankAccount bankAccount = DummyService.createDummyCheckingBankAccount();
    BankService bankService = DummyService.dummyBankService();
    bankService.deposit(bankAccount, new MonetaryAmount(0.12321, "USD"));
    assertDoesNotThrow(() -> {
      bankService.withdraw(bankAccount, new MonetaryAmount(0.12321, "USD"));
    });
  }

  @Test
  public void shouldThrowWhenTransferingFromInsufficientAccount(){
    BankAccount bankAccount = DummyService.createDummyCheckingBankAccount();
    BankAccount bankAccount2 = DummyService.createDummyCheckingBankAccount();
    BankService bankService = DummyService.dummyBankService();
    assertThrows(InsufficientFundsException.class, () -> {
      bankService.transfer(bankAccount, bankAccount2, new MonetaryAmount(1, "USD"));
    });
  }

  @Test
  public void successfulTransfering() throws InsufficientFundsException, UnknownCurrencyRate {
    BankAccount bankAccount = DummyService.createDummyCheckingBankAccount();
    BankAccount bankAccount2 = DummyService.createDummyCheckingBankAccount();
    BankService bankService = DummyService.dummyBankService();
    bankService.deposit(bankAccount, new MonetaryAmount(0.66, "USD"));
    assertThat(bankAccount.toMonetaryAmount().getValue()).isEqualByComparingTo(new BigDecimal("0.66"));
    assertThat(bankAccount2.toMonetaryAmount().getValue()).isEqualByComparingTo(new BigDecimal("0"));
    bankService.transfer(bankAccount, bankAccount2, new MonetaryAmount(0.66, "USD"));
    assertThat(bankAccount2.toMonetaryAmount().getValue()).isEqualByComparingTo(new BigDecimal("0.66"));
    assertThat(bankAccount.toMonetaryAmount().getValue()).isEqualByComparingTo(new BigDecimal("0"));
  }
}
