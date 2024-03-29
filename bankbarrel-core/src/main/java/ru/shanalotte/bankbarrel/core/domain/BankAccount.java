package ru.shanalotte.bankbarrel.core.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import ru.shanalotte.bankbarrel.core.misc.NonManagedBySpringBootPropertiesLoader;

@SuppressWarnings("checkstyle:MissingJavadocType")
@Entity
@Table(name = "bank_accounts")
public class BankAccount {

  @Id
  protected String identifier;
  @ManyToOne
  @JoinColumn(name = "owner")
  protected BankClient owner;
  protected String number = "";
  @Column(name = "type")
  protected String bankAccountType;
  @Column(name = "additional_type")
  protected String additionalType;
  protected String description;
  @Column(name = "balance")
  protected BigDecimal value = BigDecimal.valueOf(0L);
  protected String currency;

  public BankAccount() {
  }

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

  public void setOwner(BankClient owner) {
    this.owner = owner;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getBankAccountType() {
    return bankAccountType;
  }

  public void setBankAccountType(String bankAccountType) {
    this.bankAccountType = bankAccountType;
  }

  public String getAdditionalType() {
    return additionalType;
  }

  public void setAdditionalType(String additionalType) {
    this.additionalType = additionalType;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setCurrency(String currency) {
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

  public MonetaryAmount toMonetaryAmount() {
    return new MonetaryAmount(value, currency);
  }

  @SuppressWarnings("checkstyle:MissingJavadocType")
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

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public BankAccount build() {
      validateBuildParameters();
      BankAccount account = new BankAccount(owner);
      account.bankAccountType = bankAccountType.name();
      account.additionalType = additionalType.name();
      account.description = bankAccountType + " " + additionalType;
      if (currency == null) {
        account.currency = NonManagedBySpringBootPropertiesLoader.get(
            "bank.account.defaultCurrency");
      } else {
        account.currency = currency;
      }
      return account;
    }
  }

}
