package ru.shanalotte.bankbarrel.core;

import java.math.RoundingMode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.shanalotte.bankbarrel.core.domain.*;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;
import ru.shanalotte.bankbarrel.core.service.SimpleBankService;
import ru.shanalotte.bankbarrel.core.service.CurrencyConverterService;
import ru.shanalotte.bankbarrel.core.service.CurrencyRateService;

public class MonetaryAmountTest {

  @Test
  public void oneDollar_shouldEqualsTo_20rubles() throws UnknownCurrencyRate {

    CurrencyRateRule rule = new CurrencyRateRule.Builder()
        .currency("RUB")
        .is(20)
        .perOneUnitOfDefaultCurrency();

    CurrencyRateRule rule2 = new CurrencyRateRule.Builder()
        .currency("GOLD")
        .is(1000)
        .defaultCurrencyUnits();

    CurrencyRateService currencyRateService = new CurrencyRateService("USD");
    currencyRateService.addRule(rule);
    currencyRateService.addRule(rule2);
    BankAccount bankAccount = DummyService.createDummyCheckingBankAccount();
    SimpleBankService bankService = new SimpleBankService(currencyRateService, new CurrencyConverterService(10), "USD");
    bankService.deposit(bankAccount, new MonetaryAmount(1, "USD"));
    bankService.deposit(bankAccount, new MonetaryAmount(20, "RUB"));
    assertThat(bankAccount.toMonetaryAmount()).isEqualTo(new MonetaryAmount(2, "USD"));
    assertThat(bankAccount.toMonetaryAmount()).isNotEqualTo(new MonetaryAmount(2.1, "USD"));
  }

  @Test
  public void rubleAccountTest() throws UnknownCurrencyRate {
    BankAccount bankAccount = new BankAccount.Builder()
        .withOwner(DummyService.createDummyCustomer())
        .withCurrency("RUB")
        .withType(BankAccountType.CHECKING)
        .withAdditionalType(BankAccountAdditionalType.PREMIUM)
        .build();
    SimpleBankService bankService = DummyService.dummyBankService();
    bankService.deposit(bankAccount, new MonetaryAmount(10000, "RUB"));
    assertThat(bankAccount.toMonetaryAmount().getValue().setScale(4, RoundingMode.HALF_UP)).isEqualByComparingTo(new MonetaryAmount(10000, "RUB").getValue());
  }

  @Test
  public void rubleAccountMultiCurrenciesTest() throws UnknownCurrencyRate {
    BankAccount bankAccount = new BankAccount.Builder()
        .withOwner(DummyService.createDummyCustomer())
        .withCurrency("RUB")
        .withType(BankAccountType.CHECKING)
        .withAdditionalType(BankAccountAdditionalType.PREMIUM)
        .build();
    SimpleBankService bankService = DummyService.dummyBankService();
    bankService.deposit(bankAccount, new MonetaryAmount(1000, "RUB"));
    bankService.deposit(bankAccount, new MonetaryAmount(1, "USD"));
    bankService.deposit(bankAccount, new MonetaryAmount(300, "KZT"));
  }

  @Test
  public void withdraw() throws InsufficientFundsException, UnknownCurrencyRate {
    SimpleBankService bankService = DummyService.dummyBankService();
    BankAccount account = DummyService.createDummyCheckingBankAccount();
    bankService.deposit(account, new MonetaryAmount(10, "USD"));
    System.out.println(account.balance());
    bankService.withdraw(account, new MonetaryAmount(0.39, "USD"));
    System.out.println(account.balance());
  }


  @Test
  public void unknownCurrency() {
    SimpleBankService bankService = DummyService.dummyBankService();
    BankAccount bankAccount = DummyService.createDummyCheckingBankAccount();
    assertThrows(UnknownCurrencyRate.class, () -> {
      bankService.deposit(bankAccount, new MonetaryAmount(123, "XXX"));
    });
  }

}
