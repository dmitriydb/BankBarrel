package ru.shanalotte.bankbarrel.core.dto;

import java.util.Objects;

public class BankClientDto {
  private Long id;
  private String givenName;
  private String familyName;
  private String telephone;
  private String email;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BankClientDto that = (BankClientDto) o;
    return Objects.equals(givenName, that.givenName) && Objects.equals(familyName, that.familyName) && Objects.equals(telephone, that.telephone) && Objects.equals(email, that.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(givenName, familyName, telephone, email);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("BankClientDto{");
    sb.append("id=").append(id);
    sb.append(", givenName='").append(givenName).append('\'');
    sb.append(", familyName='").append(familyName).append('\'');
    sb.append(", telephone='").append(telephone).append('\'');
    sb.append(", email='").append(email).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
