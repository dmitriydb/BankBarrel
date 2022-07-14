package ru.shanalotte.bankbarrel.core.domain;

import java.math.BigDecimal;
import java.util.Objects;
import ru.shanalotte.bankbarrel.core.misc.NonManagedBySpringBootPropertiesLoader;

public class MonetaryAmount {
  private final BigDecimal value;
  private final String currency;

  public MonetaryAmount(double value, String currency) {
    this.value = BigDecimal.valueOf(value);
    this.currency = currency;
  }

  public MonetaryAmount(long value, String currency) {
    this.value = BigDecimal.valueOf(value);
    this.currency = currency;
  }

  public MonetaryAmount(int value, String currency) {
    this.value = BigDecimal.valueOf(value);
    this.currency = currency;
  }

  public MonetaryAmount(long value) {
    this.value = new BigDecimal(value);
    this.currency = NonManagedBySpringBootPropertiesLoader.get("bank.monetaryAmount.defaultCurrency");
  }

  public MonetaryAmount(double value) {
    this.value = new BigDecimal(String.valueOf(value));
    this.currency = NonManagedBySpringBootPropertiesLoader.get("bank.monetaryAmount.defaultCurrency");
  }

  public MonetaryAmount(int value) {
    this.value = new BigDecimal(value);
    this.currency = NonManagedBySpringBootPropertiesLoader.get("bank.monetaryAmount.defaultCurrency");
  }

  public MonetaryAmount(BigDecimal value, String currency) {
    this.value = value;
    this.currency = currency;
  }

  public BigDecimal getValue() {
    return value;
  }

  public String getCurrency() {
    return currency;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MonetaryAmount that = (MonetaryAmount) o;
    return (this.value.compareTo(that.value) == 0) && Objects.equals(currency, that.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, currency);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("MonetaryAmount{");
    sb.append("value=").append(value);
    sb.append(", currency='").append(currency).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
