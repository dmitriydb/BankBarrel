package ru.shanalotte.bankbarrel.core.config;

/**
 * Simple config for application constants.
 */
public class DefaultCurrenciesConfig {

  public String defaultMonetaryAmountCurrency() {
    return "USD";
  }

  public String defaultBankAccountCurrency() {
    return "USD";
  }

  public String defaultCurrencyRateCurrency() {
    return "USD";
  }

  public int defaultBigDecimalScale() {
    return 10;
  }
}
