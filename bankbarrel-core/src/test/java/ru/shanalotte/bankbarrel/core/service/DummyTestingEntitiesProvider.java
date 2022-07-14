package ru.shanalotte.bankbarrel.core.service;

import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.BankAccountAdditionalType;
import ru.shanalotte.bankbarrel.core.domain.BankAccountType;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.domain.CurrencyRateRule;
import static ru.shanalotte.bankbarrel.core.service.DummyTestingEntitiesConstants.VALID_BANK_CLIENT_EMAIL;
import static ru.shanalotte.bankbarrel.core.service.DummyTestingEntitiesConstants.VALID_BANK_CLIENT_NAME;
import static ru.shanalotte.bankbarrel.core.service.DummyTestingEntitiesConstants.VALID_BANK_CLIENT_SURNAME;

public class DummyTestingEntitiesProvider {

  public static BankClient createValidBankClientDummy(){
    return new BankClient.Builder(VALID_BANK_CLIENT_NAME, VALID_BANK_CLIENT_SURNAME)
        .withEmail(VALID_BANK_CLIENT_EMAIL)
        .build();
  }

  public static BankAccount createCheckingBankAccountDummy() {
    return new BankAccount.Builder()
        .withOwner(createValidBankClientDummy())
        .withType(BankAccountType.CHECKING)
        .withCurrency("USD")
        .withAdditionalType(BankAccountAdditionalType.TRADITIONAL)
        .build();
  }

  public static CurrencyRateService testingCurrencyRateService() {
    CurrencyRateRule dollarRule = new CurrencyRateRule.Builder()
        .currency("USD")
        .is(1)
        .perOneUnitOfDefaultCurrency();
    CurrencyRateRule rubleRule = new CurrencyRateRule.Builder()
        .currency("RUB")
        .is(20.5)
        .perOneUnitOfDefaultCurrency();
    CurrencyRateRule tengeRule = new CurrencyRateRule.Builder()
        .currency("KZT")
        .is(112.34)
        .perOneUnitOfDefaultCurrency();
    CurrencyRateService currencyRateService = new CurrencyRateService("USD");
    currencyRateService.addRule(dollarRule);
    currencyRateService.addRule(tengeRule);
    currencyRateService.addRule(rubleRule);
    return currencyRateService;
  }

  public static SimpleBankService createTestingSimpleBankService() {
    SimpleBankService bankService = new SimpleBankService(testingCurrencyRateService(), new CurrencyConverterService(10), "USD");
    return bankService;
  }
}
