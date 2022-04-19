package ru.shanalotte.bankbarrel.core;

import java.util.Objects;
import java.util.UUID;

/**
 * Class represents a single bank account.
 */
public class BankAccount {
  protected final Customer owner;
  protected final String identifier;
  protected BankAccountType bankAccountType;
  protected BankAccountAdditionalType additionalType;
  protected String description;

  private BankAccount(Customer owner) {
    this.owner = owner;
    identifier = UUID.randomUUID().toString();
  }

  public String getIdentifier() {
    return identifier;
  }

  public Customer getOwner() {
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
    sb.append("identifier='").append(identifier).append('\'');
    sb.append(", bankAccountType=").append(bankAccountType);
    sb.append(", additionalType=").append(additionalType);
    sb.append(", description='").append(description).append('\'');
    sb.append('}');
    return sb.toString();
  }

  /**
   * Builder for the BankAccount class.
   */
  public static class Builder {
    private Customer owner;
    private BankAccountAdditionalType additionalType;
    private BankAccountType bankAccountType;

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

    public Builder withOwner(Customer owner) {
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
      return account;
    }
  }
}
