package ru.shanalotte.bankbarrel.core.dto;

/**
 * DTO банковского счета.
 */
public class BankAccountDto {

  private Long owner;
  private String identifier;
  private String number;
  private String type;
  private String additionalType;
  private String description;
  private String currency;
  private String balance;

  public Long getOwner() {
    return owner;
  }

  public void setOwner(Long owner) {
    this.owner = owner;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

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

  public String getBalance() {
    return balance;
  }

  public void setBalance(String balance) {
    this.balance = balance;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("AccountController{");
    sb.append("owner=").append(owner);
    sb.append(", identifier='").append(identifier).append('\'');
    sb.append(", number='").append(number).append('\'');
    sb.append(", type='").append(type).append('\'');
    sb.append(", additionalType='").append(additionalType).append('\'');
    sb.append(", description='").append(description).append('\'');
    sb.append(", currency='").append(currency).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
