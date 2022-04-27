package ru.shanalotte.bankbarrel.core.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.CurrencyRateRule;

/**
 * Class that stores all actual currency rate rules for the current bank service.
 */
@Service
public class CurrencyRateService {


  private String defaultRateCurrency;
  private Set<CurrencyRateRule> currencyRateRules = new HashSet<>();

  /**
   * Конструктор со всеми зависимостями.
   *
   * @param defaultRateCurrency валюта, через которую всё конвертируется по умолчанию.
   */
  public CurrencyRateService(@Value("${bank.currency.defaultRateCurrency}")
                                 String defaultRateCurrency) {
    this.defaultRateCurrency = defaultRateCurrency;
    CurrencyRateRule defaultRule = new CurrencyRateRule.Builder()
        .currency(defaultRateCurrency)
        .is(1)
        .perOneUnitOfDefaultCurrency();
    this.currencyRateRules.add(defaultRule);
  }

  public void addRule(CurrencyRateRule rule) {
    currencyRateRules.add(rule);
  }

  public Set<CurrencyRateRule> getCurrencyRateRules() {
    return currencyRateRules;
  }
}
