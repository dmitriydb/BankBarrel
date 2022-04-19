package ru.shanalotte.bankbarrel.core;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * Class that represents a single bank customer.
 */
public class Customer {
  private String givenName;
  private String familyName;
  private String telephone;
  private String email;

  private Customer(String givenName, String familyName) {
    if (StringUtils.isBlank(givenName)) {
      throw new IllegalArgumentException("Customer givenName is blank");
    }
    if (StringUtils.isBlank(familyName)) {
      throw new IllegalArgumentException("Customer familyName is blank");
    }
    this.givenName = givenName;
    this.familyName = familyName;
  }


  public String getGivenName() {
    return givenName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public String getTelephone() {
    return telephone;
  }

  public String getEmail() {
    return email;
  }

  /**
   * Builder for the class Customer.
   * Checks the reqiurements for the Customer instance initial data
   * For example, throws exception if both email and telephone are not present
   */
  public static class Builder {
    private final Customer customer;

    public Builder(String givenName, String familyName) {
      this.customer = new Customer(givenName, familyName);
    }

    public Builder withEmail(String email) {
      customer.email = email;
      return this;
    }

    public Builder withTelephone(String telephone) {
      customer.telephone = telephone;
      return this;
    }

    /**
     * Build the Customer instance.
     */
    public Customer build() {
      if (StringUtils.isBlank(customer.email) && StringUtils.isBlank(customer.telephone)) {
        throw new IllegalStateException("Customer creation error: should fill email or telephone.");
      }
      return customer;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Customer customer = (Customer) o;
    return givenName.equals(customer.givenName) && familyName.equals(customer.familyName)
        && Objects.equals(telephone, customer.telephone)
        && Objects.equals(email, customer.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(givenName, familyName, telephone, email);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Customer{");
    sb.append("givenName='").append(givenName).append('\'');
    sb.append(", familyName='").append(familyName).append('\'');
    sb.append(", telephone='").append(telephone).append('\'');
    sb.append(", email='").append(email).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
