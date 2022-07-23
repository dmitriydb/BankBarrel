package ru.shanalotte.bankbarrel.appserver.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.appserver.repository.CurrencyDao;
import ru.shanalotte.bankbarrel.appserver.repository.CurrencyRateDao;
import ru.shanalotte.bankbarrel.core.domain.CurrencyRateRule;
import ru.shanalotte.bankbarrel.core.service.CurrencyRateService;

/**
 * Сервис, который загружает существующие курсы валюты из БД
 * во время запуска приложения.
 */
@Service
public class CurrencyLoader {

  private boolean areLoaded = false;

  private CurrencyDao currencyDao;
  private CurrencyRateDao currencyRateDao;
  private CurrencyRateService currencyRateService;

  /**
   * Конструктор со всеми зависимостями.
   */
  public CurrencyLoader(CurrencyDao currencyDao, CurrencyRateDao currencyRateDao,
                        CurrencyRateService currencyRateService) {
    this.currencyDao = currencyDao;
    this.currencyRateDao = currencyRateDao;
    this.currencyRateService = currencyRateService;
  }

  /**
   * Загружает валюты 1 раз при запуске приложения.
   */
  @Scheduled(initialDelay = 1000, fixedDelay = Integer.MAX_VALUE)
  public void loadCurrenciesInService() {
    if (areLoaded) {
      return;
    }
    for (CurrencyRateRule rule : currencyRateDao.findAll()) {
      currencyRateService.addRule(rule);
    }
    areLoaded = true;
  }
}
