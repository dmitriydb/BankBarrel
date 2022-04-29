package ru.shanalotte.bankbarrel.webapp.config;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.CurrencyRateRule;
import ru.shanalotte.bankbarrel.core.service.CurrencyRateService;

@Profile("dev")
@Service
public class DevCurrencyRatesConfig {

  private CurrencyRateService currencyRateService;

  public DevCurrencyRatesConfig(CurrencyRateService currencyRateService) {
    this.currencyRateService = currencyRateService;
  }

  @Scheduled(initialDelay = 500, fixedDelay = Integer.MAX_VALUE)
  public void setDevCurrencyRates() {
    CurrencyRateRule currencyRateRule = new CurrencyRateRule.Builder()
        .currency("RUB")
        .is(100)
        .perOneUnitOfDefaultCurrency();
    CurrencyRateRule currencyRateRule2 = new CurrencyRateRule.Builder()
        .currency("KZT")
        .is(500)
        .perOneUnitOfDefaultCurrency();
    currencyRateService.addRule(currencyRateRule);
    currencyRateService.addRule(currencyRateRule2);
  }
}
