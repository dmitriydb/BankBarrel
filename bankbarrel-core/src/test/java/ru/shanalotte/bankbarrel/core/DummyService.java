package ru.shanalotte.bankbarrel.core;

import static ru.shanalotte.bankbarrel.core.CustomerCreationData.validName;
import static ru.shanalotte.bankbarrel.core.CustomerCreationData.validSurname;
import ru.shanalotte.bankbarrel.core.service.BankService;
import ru.shanalotte.bankbarrel.core.service.CurrencyConverterService;
import ru.shanalotte.bankbarrel.core.service.CurrencyRateService;

public class DummyService {

  public static Customer createDummyCustomer(){
    return new Customer.Builder(validName, validSurname)
        .withEmail("abc@xdf")
        .build();
  }

  public static BankAccount createDummyCheckingBankAccount() {
    return new BankAccount.Builder()
        .withOwner(createDummyCustomer())
        .withType(BankAccountType.CHECKING)
        .withCurrency("USD")
        .withAdditionalType(BankAccountAdditionalType.TRADITIONAL)
        .build();
  }

  public static CurrencyRateService defaultCurrencyRateService() {
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
    CurrencyRateService currencyRateService = new CurrencyRateService();
    currencyRateService.addRule(dollarRule);
    currencyRateService.addRule(tengeRule);
    currencyRateService.addRule(rubleRule);
    return currencyRateService;
  }

  public static BankService dummyBankService() {
    BankService bankService = new BankService(defaultCurrencyRateService(), new CurrencyConverterService());
    return bankService;
  }
}
