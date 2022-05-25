package ru.shanalotte.bankbarrel.webapp.user;

import java.time.LocalDateTime;
import org.springframework.cglib.core.Local;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;

/**
 * Web app user.
 * Linked to the single (only one!) bank client.
 */
public class WebAppUser {
  private Long id;
  private String username;
  private BankClientDto bankClient;
  private LocalDateTime registrationTs;
  private LocalDateTime lastLoginTs;

  public WebAppUser(String username, BankClientDto bankClient) {
    this.username = username;
    System.out.println(bankClient.toString());
    this.bankClient = bankClient;
  }

  public WebAppUser() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public BankClientDto getBankClient() {
    return bankClient;
  }

  public void setBankClient(BankClientDto bankClient) {
    this.bankClient = bankClient;
  }

  public LocalDateTime getRegistrationTs() {
    return registrationTs;
  }

  public void setRegistrationTs(LocalDateTime registrationTs) {
    this.registrationTs = registrationTs;
  }

  public LocalDateTime getLastLoginTs() {
    return lastLoginTs;
  }

  public void setLastLoginTs(LocalDateTime lastLoginTs) {
    this.lastLoginTs = lastLoginTs;
  }

  public String getUsername() {
    return username;
  }

  public BankClientDto getClient() {
    return bankClient;
  }
}
