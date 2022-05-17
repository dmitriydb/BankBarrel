package ru.shanalotte.bankbarrel.webapp.user;

import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;

/**
 * Web app user.
 * Linked to the single (only one!) bank client.
 */
public class WebAppUser {
  private final String username;
  private final BankClientDto bankClient;

  public WebAppUser(String username, BankClientDto bankClient) {
    this.username = username;
    this.bankClient = bankClient;
  }

  public String getUsername() {
    return username;
  }

  public BankClientDto getClient() {
    return bankClient;
  }
}
