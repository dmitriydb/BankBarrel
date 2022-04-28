package ru.shanalotte.bankbarrel.webapp.dto;

public class BankAccountDetailsDto {
  private String number;
  private String type;
  private String additionalType;
  private String description;
  private String currency;
  private String currencySign;
  private String balance;

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getAdditionalType() {
    return additionalType;
  }

  public void setAdditionalType(String additionalType) {
    this.additionalType = additionalType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getCurrencySign() {
    return currencySign;
  }

  public void setCurrencySign(String currencySign) {
    this.currencySign = currencySign;
  }

  public String getBalance() {
    return balance;
  }

  public void setBalance(String balance) {
    this.balance = balance;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("BankAccountDetailsDto{");
    sb.append("number='").append(number).append('\'');
    sb.append(", type='").append(type).append('\'');
    sb.append(", additionalType='").append(additionalType).append('\'');
    sb.append(", description='").append(description).append('\'');
    sb.append(", currency='").append(currency).append('\'');
    sb.append(", currencySign='").append(currencySign).append('\'');
    sb.append(", balance='").append(balance).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
