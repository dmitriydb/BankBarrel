package ru.shanalotte.bankbarrel.core.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.CurrencyRateRule;
import ru.shanalotte.bankbarrel.core.exception.CurrencyNotRegisteredInSystemException;

@Service
public class CurrencyConverterService {

  private int bigDecimalRoundingScale;

  public CurrencyConverterService(
      @Value("${bank.bigdecimal.scale}") int bigDecimalRoundingScale) {
    this.bigDecimalRoundingScale = bigDecimalRoundingScale;
  }

  /**
   * Converts a monetary amount to the monetary amount of default currency.
   *
   * @param currencyRateService service that stores all available currency rates
   * @param currency            currency
   * @param value               monetary amount
   * @return converted value
   * @throws CurrencyNotRegisteredInSystemException when the desired currency rate is not known
   */
  public BigDecimal convertToDefaultCurrency(CurrencyRateService currencyRateService,
                                             String currency, BigDecimal value)
      throws CurrencyNotRegisteredInSystemException {
    Optional<CurrencyRateRule> tradingRuleForCurrency = currencyRateService.findTradingRateForCurrency(currency);
    if (tradingRuleForCurrency.isPresent()) {
      BigDecimal currencyRate = tradingRuleForCurrency.get().getRate();
      boolean isMore = tradingRuleForCurrency.get().isMore();
      if (isMore) {
        return value.multiply(currencyRate);
      } else {
        return value.divide(currencyRate, bigDecimalRoundingScale, RoundingMode.HALF_UP);
      }
    } else {
      throw new CurrencyNotRegisteredInSystemException("Currency rate rule for " + currency + " is not registered in the system!");
    }
  }

  /**
   * Converts a monetary amount from the default currency to the desired currency.
   *
   * @param currencyRateService service that stores all available currency rates
   * @param toCurrency          desired monetary amount currency
   * @param value               value of money
   * @return converted value
   * @throws CurrencyNotRegisteredInSystemException when the desired currency rate is not known
   */
  public BigDecimal convertFromDefaultCurrency(CurrencyRateService currencyRateService,
                                               String toCurrency,
                                               BigDecimal value) throws CurrencyNotRegisteredInSystemException {
    Optional<CurrencyRateRule> tradingRuleForCurrency = currencyRateService.findTradingRateForCurrency(toCurrency);
    if (tradingRuleForCurrency.isPresent()) {
      BigDecimal currencyRate = tradingRuleForCurrency.get().getRate();
      boolean isMore = tradingRuleForCurrency.get().isMore();
      if (!isMore) {
        return value.multiply(currencyRate);
      } else {
        return value.divide(currencyRate, bigDecimalRoundingScale, RoundingMode.DOWN);
      }
    } else {
      throw new CurrencyNotRegisteredInSystemException(
          "Currency rate rule for " + toCurrency + " is not found!");
    }
  }

}
