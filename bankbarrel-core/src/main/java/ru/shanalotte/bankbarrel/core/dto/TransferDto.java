package ru.shanalotte.bankbarrel.core.dto;

import java.math.BigDecimal;

public class TransferDto {
  private Long id;
  private String fromAccount;
  private String toAccount;
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

  public String getFromAccount() {
    return fromAccount;
  }

  public void setFromAccount(String fromAccount) {
    this.fromAccount = fromAccount;
  }

  public String getToAccount() {
    return toAccount;
  }

  public void setToAccount(String toAccount) {
    this.toAccount = toAccount;
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
    final StringBuilder sb = new StringBuilder("MoneyTransferDto{");
    sb.append("id=").append(id);
    sb.append(", fromAccount='").append(fromAccount).append('\'');
    sb.append(", toAccount='").append(toAccount).append('\'');
    sb.append(", amount=").append(amount);
    sb.append(", timestamp='").append(timestamp).append('\'');
    sb.append(", currency='").append(currency).append('\'');
    sb.append(", source='").append(source).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
