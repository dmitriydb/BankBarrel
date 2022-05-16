package ru.shanalotte.bankbarrel.core.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class DepositDto {
  private Long id;
  private String account;
  private BigDecimal amount;
  private String timestamp;
  private String currency;
  private String source;
  private String result;

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("DepositDto{");
    sb.append("id=").append(id);
    sb.append(", account='").append(account).append('\'');
    sb.append(", amount=").append(amount);
    sb.append(", timestamp='").append(timestamp).append('\'');
    sb.append(", currency='").append(currency).append('\'');
    sb.append(", source='").append(source).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
