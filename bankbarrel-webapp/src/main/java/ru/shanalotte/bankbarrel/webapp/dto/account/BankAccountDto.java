package ru.shanalotte.bankbarrel.webapp.dto.account;

/**
 * DTO для бизнес-объекта BankAccount.
 */
public class BankAccountDto {
  private final String number;
  private final String type;
  private final String additionalType;
  private final String currency;

  /**
   * Конструктор.
   */
  public BankAccountDto(String number, String type, String additionalType, String currency) {
    this.number = number;
    this.type = type;
    this.additionalType = additionalType;
    this.currency = currency;
  }

  public String getNumber() {
    return number;
  }

  public String getType() {
    return type;
  }

  public String getAdditionalType() {
    return additionalType;
  }

  public String getCurrency() {
    return currency;
  }
}
