package ru.shanalotte.bankbarrel.webapp.dao;

import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

/**
 * DAO для пользователей веб-приложения.
 */
public interface WebAppUserDao {

  WebAppUser findByUsername(String username);

  void addUser(WebAppUser webAppUser);

  boolean isUserExists(String username);

  int count();
}
