package ru.shanalotte.bankbarrel.webapp.dto.transfer;

import javax.validation.constraints.NotEmpty;

/**
 * DTO для операции перевода.
 * Содержит поля для поиска, введенные пользователем, сумму и валюту.
 */
public class TransferDto {
  @NotEmpty
  private String accountNumber;
  private Double amount = 0.0;
  @NotEmpty
  private String currency;

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }
}
