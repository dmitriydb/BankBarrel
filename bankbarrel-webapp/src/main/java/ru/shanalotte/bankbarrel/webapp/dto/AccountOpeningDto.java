package ru.shanalotte.bankbarrel.webapp.dto;

public class AccountOpeningDto {
  private String accountType;
  private String accountAdditionalType;
  private String currency;

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getAccountAdditionalType() {
    return accountAdditionalType;
  }

  public void setAccountAdditionalType(String accountAdditionalType) {
    this.accountAdditionalType = accountAdditionalType;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("AccountOpeningDto{");
    sb.append("accountType='").append(accountType).append('\'');
    sb.append(", accountAdditionalType='").append(accountAdditionalType).append('\'');
    sb.append(", currency='").append(currency).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
