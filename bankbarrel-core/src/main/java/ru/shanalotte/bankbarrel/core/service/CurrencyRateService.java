package ru.shanalotte.bankbarrel.core.service;

import java.util.HashSet;
import java.util.Set;
import ru.shanalotte.bankbarrel.core.CurrencyRateRule;
import ru.shanalotte.bankbarrel.core.config.DefaultCurrenciesConfig;

/**
 * Class that stores all actual currency rate rules for the current bank service.
 */
public class CurrencyRateService {

  /**
   * Constructor that adds base rule: 1 unit of default currency = 1 unit of default currency.
   */
  public CurrencyRateService() {
    CurrencyRateRule defaultRule = new CurrencyRateRule.Builder()
        .currency(new DefaultCurrenciesConfig().defaultCurrencyRateCurrency())
        .is(1)
        .perOneUnitOfDefaultCurrency();
    this.currencyRateRules.add(defaultRule);
  }

  private Set<CurrencyRateRule> currencyRateRules = new HashSet<>();

  public void addRule(CurrencyRateRule rule) {
    currencyRateRules.add(rule);
  }

  public Set<CurrencyRateRule> getCurrencyRateRules() {
    return currencyRateRules;
  }
}
