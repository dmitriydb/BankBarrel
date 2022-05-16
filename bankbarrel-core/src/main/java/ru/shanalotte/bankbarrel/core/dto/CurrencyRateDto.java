package ru.shanalotte.bankbarrel.core.dto;

import java.math.BigDecimal;

public class CurrencyRateDto {

  private String currency;
  private BigDecimal rate;
  private boolean more;

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }

  public boolean isMore() {
    return more;
  }

  public void setMore(boolean more) {
    this.more = more;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CurrencyRateDto{");
    sb.append("currency='").append(currency).append('\'');
    sb.append(", rate=").append(rate);
    sb.append(", more=").append(more);
    sb.append('}');
    return sb.toString();
  }
}
