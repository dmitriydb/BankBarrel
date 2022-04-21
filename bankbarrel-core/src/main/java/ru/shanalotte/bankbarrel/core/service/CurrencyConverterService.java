package ru.shanalotte.bankbarrel.core.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.CurrencyRateRule;
import ru.shanalotte.bankbarrel.core.exception.CurrencyNotFoundException;

/**
 * Class that performs basic currencies converting operations.
 */
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
   * @param currency            monetary amount
   * @param value               value
   * @return converted value
   * @throws CurrencyNotFoundException when the desired currency rate is not known
   */
  public BigDecimal convertToDefaultCurrency(CurrencyRateService currencyRateService,
                                             String currency, BigDecimal value)
      throws CurrencyNotFoundException {
    Optional<CurrencyRateRule> currencyRateRule = currencyRateService.getCurrencyRateRules()
        .stream().filter(e -> e.getCurrency().equals(currency))
        .findFirst();
    if (currencyRateRule.isPresent()) {
      BigDecimal rate = currencyRateRule.get().getRate();
      boolean isMore = currencyRateRule.get().isMore();
      if (isMore) {
        return value.multiply(rate);
      } else {
        return value.divide(rate, bigDecimalRoundingScale, RoundingMode.HALF_UP);
      }
    } else {
      throw new CurrencyNotFoundException("Currency rate rule for " + currency + " is not found!");
    }
  }

  /**
   * Converts a monetary amount from the default currency to the desired currency.
   *
   * @param currencyRateService service that stores all available currency rates
   * @param toCurrency          desired monetary amount currency
   * @param value               value of money
   * @return converted value
   * @throws CurrencyNotFoundException when the desired currency rate is not known
   */
  public BigDecimal convertFromDefaultCurrency(CurrencyRateService currencyRateService,
                                               String toCurrency,
                                               BigDecimal value) throws CurrencyNotFoundException {
    Optional<CurrencyRateRule> currencyRateRule = currencyRateService.getCurrencyRateRules()
        .stream().filter(e -> e.getCurrency().equals(toCurrency))
        .findFirst();

    if (currencyRateRule.isPresent()) {
      BigDecimal rate = currencyRateRule.get().getRate();
      boolean isMore = currencyRateRule.get().isMore();
      if (!isMore) {
        return value.multiply(rate);
      } else {
        return value.divide(rate, bigDecimalRoundingScale, RoundingMode.DOWN);
      }
    } else {
      throw new CurrencyNotFoundException(
          "Currency rate rule for " + toCurrency + " is not found!");
    }
  }

}
