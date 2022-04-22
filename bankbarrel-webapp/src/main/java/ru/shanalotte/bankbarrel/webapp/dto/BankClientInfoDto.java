package ru.shanalotte.bankbarrel.webapp.dto;

import javax.validation.constraints.NotEmpty;

/**
 * DTO for the login process (creating a bank customer).
 */
public class BankClientInfoDto {

  @NotEmpty
  private String firstName;
  @NotEmpty
  private String lastName;
  private String telephone;
  private String email;

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getTelephone() {
    return telephone;
  }

  public String getEmail() {
    return email;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("BankClientInfoDto{");
    sb.append("firstName='").append(firstName).append('\'');
    sb.append(", lastName='").append(lastName).append('\'');
    sb.append(", telephone='").append(telephone).append('\'');
    sb.append(", email='").append(email).append('\'');
    sb.append('}');
    return sb.toString();
  }
}