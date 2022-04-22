package ru.shanalotte.bankbarrel.webapp.user;

/**
 * Web app user.
 *
 * Linked to the single (only one!) bank client.
 */
public class WebAppUser {
  private final String username;

  public WebAppUser(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
