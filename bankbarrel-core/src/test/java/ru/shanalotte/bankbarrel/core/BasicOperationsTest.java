package ru.shanalotte.bankbarrel.core;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;
import ru.shanalotte.bankbarrel.core.service.SimpleBankService;


public class BasicOperationsTest {

  @Test
  public void deposit100DollarsToAccount() throws UnknownCurrencyRate {
    SimpleBankService bankService = DummyService.dummyBankService();
    BankAccount account = DummyService.createDummyCheckingBankAccount();
    bankService.deposit(account, new MonetaryAmount(100.0));
    assertThat(account.balance().doubleValue()).isEqualTo(100.0);
  }

  @Test
  public void newAccount_ShouldHaveZeroValue() {
    BankAccount account = DummyService.createDummyCheckingBankAccount();
    assertThat(account.balance().doubleValue()).isZero();
  }

  @Test
  public void monetaryAmountCurrencyIsUSDByDefault() {
    MonetaryAmount monetaryAmount = new MonetaryAmount(100);
    assertThat(monetaryAmount.getCurrency()).isEqualTo("USD");
  }
}
