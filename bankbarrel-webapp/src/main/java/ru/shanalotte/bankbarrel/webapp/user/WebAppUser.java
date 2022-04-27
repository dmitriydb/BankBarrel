package ru.shanalotte.bankbarrel.webapp.user;

import ru.shanalotte.bankbarrel.core.domain.BankClient;

/**
 * Web app user.
 * Linked to the single (only one!) bank client.
 */
public class WebAppUser {
  private final String username;
  private final BankClient bankClient;

  public WebAppUser(String username, BankClient bankClient) {
    this.username = username;
    this.bankClient = bankClient;
  }

  public String getUsername() {
    return username;
  }

  public BankClient getClient() {
    return bankClient;
  }
}
