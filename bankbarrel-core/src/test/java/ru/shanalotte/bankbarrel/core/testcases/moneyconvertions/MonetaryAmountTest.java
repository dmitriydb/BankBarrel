package ru.shanalotte.bankbarrel.core.testcases.moneyconvertions;

import java.math.RoundingMode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.shanalotte.bankbarrel.core.service.DummyTestingEntitiesProvider;
import ru.shanalotte.bankbarrel.core.domain.*;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRateForRequestedCurrency;
import ru.shanalotte.bankbarrel.core.service.SimpleBankService;
import ru.shanalotte.bankbarrel.core.service.CurrencyConverterService;
import ru.shanalotte.bankbarrel.core.service.CurrencyRateService;

public class MonetaryAmountTest {

  @Test
  public void oneDollar_shouldEqualsTo_20rubles() throws UnknownCurrencyRateForRequestedCurrency {
    CurrencyRateRule rubleTradingRate = new CurrencyRateRule.Builder()
        .currency("RUB")
        .is(20)
        .perOneUnitOfDefaultCurrency();
    CurrencyRateRule goldTradingRate = new CurrencyRateRule.Builder()
        .currency("GOLD")
        .is(1000)
        .defaultCurrencyUnits();
    CurrencyRateService currencyRateService = new CurrencyRateService("USD");
    currencyRateService.addRule(rubleTradingRate);
    currencyRateService.addRule(goldTradingRate);
    BankAccount bankAccount = DummyTestingEntitiesProvider.createCheckingBankAccountDummy();
    SimpleBankService bankService = new SimpleBankService(currencyRateService, new CurrencyConverterService(10), "USD");
    bankService.deposit(bankAccount, new MonetaryAmount(1, "USD"));
    bankService.deposit(bankAccount, new MonetaryAmount(20, "RUB"));
    assertThat(bankAccount.toMonetaryAmount()).isEqualTo(new MonetaryAmount(2, "USD"));
    assertThat(bankAccount.toMonetaryAmount()).isNotEqualTo(new MonetaryAmount(2.1, "USD"));
  }

  @Test
  public void rubleAccountTest() throws UnknownCurrencyRateForRequestedCurrency {
    BankAccount bankAccount = new BankAccount.Builder()
        .withOwner(DummyTestingEntitiesProvider.createValidBankClientDummy())
        .withCurrency("RUB")
        .withType(BankAccountType.CHECKING)
        .withAdditionalType(BankAccountAdditionalType.PREMIUM)
        .build();
    SimpleBankService bankService = DummyTestingEntitiesProvider.createTestingSimpleBankService();
    bankService.deposit(bankAccount, new MonetaryAmount(10000, "RUB"));
    assertThat(bankAccount.toMonetaryAmount().getValue().setScale(4, RoundingMode.HALF_UP)).isEqualByComparingTo(new MonetaryAmount(10000, "RUB").getValue());
  }

  @Test
  public void rubleAccountMultiCurrenciesTest() throws UnknownCurrencyRateForRequestedCurrency {
    BankAccount bankAccount = new BankAccount.Builder()
        .withOwner(DummyTestingEntitiesProvider.createValidBankClientDummy())
        .withCurrency("RUB")
        .withType(BankAccountType.CHECKING)
        .withAdditionalType(BankAccountAdditionalType.PREMIUM)
        .build();
    SimpleBankService bankService = DummyTestingEntitiesProvider.createTestingSimpleBankService();
    bankService.deposit(bankAccount, new MonetaryAmount(1000, "RUB"));
    bankService.deposit(bankAccount, new MonetaryAmount(1, "USD"));
    bankService.deposit(bankAccount, new MonetaryAmount(300, "KZT"));
  }

  @Test
  public void withdraw() throws InsufficientFundsException, UnknownCurrencyRateForRequestedCurrency {
    SimpleBankService bankService = DummyTestingEntitiesProvider.createTestingSimpleBankService();
    BankAccount account = DummyTestingEntitiesProvider.createCheckingBankAccountDummy();
    bankService.deposit(account, new MonetaryAmount(10, "USD"));
    System.out.println(account.balance());
    bankService.withdraw(account, new MonetaryAmount(0.39, "USD"));
    System.out.println(account.balance());
  }


  @Test
  public void unknownCurrency() {
    SimpleBankService bankService = DummyTestingEntitiesProvider.createTestingSimpleBankService();
    BankAccount bankAccount = DummyTestingEntitiesProvider.createCheckingBankAccountDummy();
    assertThrows(UnknownCurrencyRateForRequestedCurrency.class, () -> {
      bankService.deposit(bankAccount, new MonetaryAmount(123, "XXX"));
    });
  }

}
