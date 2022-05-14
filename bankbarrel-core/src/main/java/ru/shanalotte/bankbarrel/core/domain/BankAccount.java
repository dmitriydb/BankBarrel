package ru.shanalotte.bankbarrel.core.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import org.springframework.context.annotation.ComponentScan;
import ru.shanalotte.bankbarrel.core.misc.PropertiesLoader;

/**
 * Class represents a single bank account.
 */
@Entity
@Table(name = "bank_accounts")
public class BankAccount {

  @ManyToOne
  @JoinColumn(name = "owner")
  protected BankClient owner;
  @Id
  protected String identifier;
  protected String number = "";
  @Column(name = "type")
  protected BankAccountType bankAccountType;
  @Column(name = "additional_type")
  protected BankAccountAdditionalType additionalType;
  protected String description;
  @Column(name = "balance")
  protected BigDecimal value = BigDecimal.valueOf(0L);
  protected String currency;

  private BankAccount(BankClient owner) {
    this.owner = owner;
    identifier = UUID.randomUUID().toString();
  }

  public String getIdentifier() {
    return identifier;
  }

  public BankClient getOwner() {
    return owner;
  }

  public BankAccountType getBankAccountType() {
    return bankAccountType;
  }

  public BankAccountAdditionalType getAdditionalType() {
    return additionalType;
  }

  public String getDescription() {
    return description;
  }

  public BigDecimal balance() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BankAccount that = (BankAccount) o;
    return identifier.equals(that.identifier);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identifier);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("BankAccount{");
    sb.append("owner=").append(owner);
    sb.append(", identifier='").append(identifier).append('\'');
    sb.append(", bankAccountType=").append(bankAccountType);
    sb.append(", additionalType=").append(additionalType);
    sb.append(", description='").append(description).append('\'');
    sb.append(", value=").append(value);
    sb.append(", currency='").append(currency).append('\'');
    sb.append('}');
    return sb.toString();
  }

  public BigDecimal getValue() {
    return value;
  }

  public String getCurrency() {
    return currency;
  }

  public MonetaryAmount toMonetaryAmount() {
    return new MonetaryAmount(value, currency);
  }

  /**
   * Builder for the BankAccount class.
   */
  public static class Builder {
    private BankClient owner;
    private BankAccountAdditionalType additionalType;
    private BankAccountType bankAccountType;
    private String currency;

    public Builder() {

    }

    private void validateBuildParameters() {
      if (owner == null) {
        throw new IllegalStateException("Building error: owner is not set");
      }
      if (additionalType == null) {
        throw new IllegalStateException("Building error: additionalType is not set");
      }
      if (bankAccountType == null) {
        throw new IllegalStateException("Building error: bankAccountType is not set");
      }
      if (!bankAccountType.getAdditionalTypes().contains(additionalType)) {
        throw new IllegalStateException("Error, trying create " + bankAccountType
            + " account with " + additionalType + " additional type");
      }
    }

    public Builder withOwner(BankClient owner) {
      this.owner = owner;
      return this;
    }

    public Builder withType(BankAccountType bankAccountType) {
      this.bankAccountType = bankAccountType;
      return this;
    }

    public Builder withAdditionalType(BankAccountAdditionalType additionalType) {
      this.additionalType = additionalType;
      return this;
    }

    public Builder withCurrency(String currency) {
      this.currency = currency;
      return this;
    }

    /**
     * Validates all build parameters, such as: bank account type, additional type etc.
     * Throws exception if some mandatory parameters are not present or in case if
     * the additional type mismatches the main bank account type.
     * For example, you can't create the instances of the BankAccount class with
     * checking type and saving-only additional types.
     *
     * @return the instance of the BankAccount class.
     */
    public BankAccount build() {
      validateBuildParameters();
      BankAccount account = new BankAccount(owner);
      account.bankAccountType = bankAccountType;
      account.additionalType = additionalType;
      account.description = bankAccountType + " " + additionalType;
      if (currency == null) {
        account.currency = PropertiesLoader.get("bank.account.defaultCurrency");
      } else {
        account.currency = currency;
      }
      return account;
    }
  }

}
