package ru.shanalotte.bankbarrel.core.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import ru.shanalotte.bankbarrel.core.misc.PropertiesLoader;

/**
 * Class that establishes the rule for currency rate.
 * E.g. 1$ = 20RUB
 */
@Entity
@Table(name = "currency_rates")
public class CurrencyRateRule {

  private BigDecimal rate;
  /**
   * true for currencies that are bigger that USD, false otherwise.
   *
   */
  @Column(name = "is_more")
  private boolean more;
  @Id
  private String currency;

  public CurrencyRateRule() {
  }

  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }

  public void setMore(boolean more) {
    this.more = more;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  /**
   * Constructor for creating the immutable instance of the CurrencyRateRule class.
   *
   * @param currency currency
   * @param rate rate
   * @param more true for currencies that are bigger that USD, false otherwise.
   */
  public CurrencyRateRule(String currency, BigDecimal rate, boolean more) {
    this.currency = currency;
    this.rate = rate;
    this.more = more;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public boolean isMore() {
    return more;
  }

  /**
   * Builder for the currency rate class.
   * Use the building method perOneUnitOfDefaultCurrency() for the currencies
   * that cost less then dollar, and use the method defaultCurrencyUnits() otherwise.
   */
  public static class Builder {
    private String currency;
    private BigDecimal rate;

    public Builder() {

    }

    public Builder currency(String currency) {
      this.currency = currency;
      return this;
    }

    public Builder is(double rate) {
      this.rate = BigDecimal.valueOf(rate);
      return this;
    }

    public CurrencyRateRule perOneUnitOfDefaultCurrency() {
      return new CurrencyRateRule(currency, rate, false);
    }

    public CurrencyRateRule defaultCurrencyUnits() {
      return new CurrencyRateRule(currency, rate, true);
    }
  }

  public String getCurrency() {
    return currency;
  }

  @Override
  public String toString() {

    if (more) {
      return "One " + currency + " is "
          + rate + " " + PropertiesLoader.get("bank.currency.defaultRateCurrency");
    } else {
      return "One " +  PropertiesLoader.get("bank.currency.defaultRateCurrency")
           + " is " + rate + " " + currency;
    }
  }
}
