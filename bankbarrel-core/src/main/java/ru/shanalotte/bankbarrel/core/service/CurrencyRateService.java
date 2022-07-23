package ru.shanalotte.bankbarrel.core.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.CurrencyRateRule;

/**
 * Class that stores all actual currency rate rules for the current bank service.
 */
@Service
public class CurrencyRateService {

  private static final Logger logger = LoggerFactory.getLogger(CurrencyRateService.class);

  private String defaultRateCurrency;
  private Set<CurrencyRateRule> currencyRateRules = new HashSet<>();

  public CurrencyRateService(@Value("${bank.currency.defaultRateCurrency}")
                                 String defaultRateCurrency) {
    this.defaultRateCurrency = defaultRateCurrency;
    addRuleForDefaultRateCurrency(defaultRateCurrency);
  }

  private void addRuleForDefaultRateCurrency(String defaultRateCurrency) {
    CurrencyRateRule defaultRule = new CurrencyRateRule.Builder()
        .currency(defaultRateCurrency)
        .is(1)
        .perOneUnitOfDefaultCurrency();
    this.currencyRateRules.add(defaultRule);
  }

  public void addRule(CurrencyRateRule rule) {
    logger.info("Adding currency rate rule {}", rule);
    currencyRateRules.add(rule);
  }

  public Optional<CurrencyRateRule> findTradingRateForCurrency(String currency) {
    return currencyRateRules
        .stream().filter(e -> e.getCurrency().equals(currency))
        .findFirst();
  }

  public Set<CurrencyRateRule> getCurrencyRateRules() {
    return currencyRateRules;
  }
}
