package ru.shanalotte.bankbarrel.core.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.commons.lang3.StringUtils;

/**
 * Class that represents a single bank customer.
 */
@Entity
@Table(name = "bank_clients")
public class BankClient {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "identifier")
  private Long id;
  @Column(name = "given_name")
  private String givenName;
  @Column(name = "family_name")
  private String familyName;
  private String telephone;
  private String email;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "owner")
  private Set<BankAccount> accounts = new HashSet<>();

  public BankClient() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  private BankClient(String givenName, String familyName) {
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

  public Set<BankAccount> getAccounts() {
    return accounts;
  }

  public void addAccount(BankAccount bankAccount) {
    accounts.add(bankAccount);
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Builder for the class Customer.
   * Checks the reqiurements for the Customer instance initial data
   * For example, throws exception if both email and telephone are not present
   */
  public static class Builder {
    private final BankClient bankClient;

    public Builder(String givenName, String familyName) {
      this.bankClient = new BankClient(givenName, familyName);
    }

    public Builder withEmail(String email) {
      bankClient.email = email;
      return this;
    }

    public Builder withTelephone(String telephone) {
      bankClient.telephone = telephone;
      return this;
    }

    /**
     * Build the Customer instance.
     */
    public BankClient build() {
      if (StringUtils.isBlank(bankClient.email) && StringUtils.isBlank(bankClient.telephone)) {
        throw new IllegalStateException("Customer creation error: should fill email or telephone.");
      }
      return bankClient;
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
    BankClient bankClient = (BankClient) o;
    return givenName.equals(bankClient.givenName) && familyName.equals(bankClient.familyName)
        && Objects.equals(telephone, bankClient.telephone)
        && Objects.equals(email, bankClient.email);
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
