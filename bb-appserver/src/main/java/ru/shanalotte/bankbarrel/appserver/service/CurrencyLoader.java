package ru.shanalotte.bankbarrel.appserver.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.appserver.repository.CurrencyDao;
import ru.shanalotte.bankbarrel.appserver.repository.CurrencyRateDao;
import ru.shanalotte.bankbarrel.core.domain.CurrencyRateRule;
import ru.shanalotte.bankbarrel.core.service.CurrencyRateService;

@Service
public class CurrencyLoader {

  private CurrencyDao currencyDao;
  private CurrencyRateDao currencyRateDao;
  private CurrencyRateService currencyRateService;

  public CurrencyLoader(CurrencyDao currencyDao, CurrencyRateDao currencyRateDao, CurrencyRateService currencyRateService) {
    this.currencyDao = currencyDao;
    this.currencyRateDao = currencyRateDao;
    this.currencyRateService = currencyRateService;
  }

  @Scheduled(initialDelay = 1000, fixedDelay = Integer.MAX_VALUE)
  public void loadCurrenciesInService() {
    for (CurrencyRateRule rule : currencyRateDao.findAll()) {
      System.out.println("Adding rule " + rule);
      currencyRateService.addRule(rule);
    }
    System.out.println(currencyRateService.getCurrencyRateRules().size() + " rules active");
  }
}