package ru.shanalotte.bankbarrel.core.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import ru.shanalotte.bankbarrel.core.misc.NonManagedBySpringBootPropertiesLoader;

@Entity
@Table(name = "currency_rates")
public class CurrencyRateRule {

  @Id
  private String currency;
  private BigDecimal rate;

  /**
   * true for currencies that are bigger that USD, false otherwise.
   */
  @Column(name = "is_more")
  private boolean more;

  public CurrencyRateRule() {
  }

  public CurrencyRateRule(String currency, BigDecimal rate, boolean more) {
    this.currency = currency;
    this.rate = rate;
    this.more = more;
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

  public BigDecimal getRate() {
    return rate;
  }

  public boolean isMore() {
    return more;
  }

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
          + rate + " " + NonManagedBySpringBootPropertiesLoader.get("bank.currency.defaultRateCurrency");
    } else {
      return "One " +  NonManagedBySpringBootPropertiesLoader.get("bank.currency.defaultRateCurrency")
           + " is " + rate + " " + currency;
    }
  }
}
