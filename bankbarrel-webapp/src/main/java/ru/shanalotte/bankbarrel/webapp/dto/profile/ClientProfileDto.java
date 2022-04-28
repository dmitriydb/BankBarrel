package ru.shanalotte.bankbarrel.webapp.dto.profile;

import ru.shanalotte.bankbarrel.core.domain.BankClient;

/**
 * DTO для отображения профиля о пользователе веб-приложения.
 */
public class ClientProfileDto {
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String telephone;

  /**
   * Создает DTO из объекта BankClient.
   */
  public static ClientProfileDto of(BankClient client) {
    ClientProfileDto dto = new ClientProfileDto();
    dto.email = client.getEmail();
    dto.firstName = client.getGivenName();
    dto.lastName = client.getFamilyName();
    dto.telephone = client.getTelephone();
    return dto;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }
}
