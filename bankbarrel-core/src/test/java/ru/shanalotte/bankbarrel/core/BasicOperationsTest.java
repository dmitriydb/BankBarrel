package ru.shanalotte.bankbarrel.core;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import ru.shanalotte.bankbarrel.core.config.DefaultCurrenciesConfig;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;
import ru.shanalotte.bankbarrel.core.service.BankService;

public class BasicOperationsTest {

  @Test
  public void deposit100DollarsToAccount() throws UnknownCurrencyRate {
    BankService bankService = new BankService();
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
    assertThat(monetaryAmount.getCurrency()).isEqualTo(new DefaultCurrenciesConfig().defaultMonetaryAmountCurrency());
  }
}
