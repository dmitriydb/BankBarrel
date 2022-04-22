package ru.shanalotte.bankbarrel.webapp.dao;

import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

public interface WebAppUserDao {
  WebAppUser findByUsername(String username);
  void addUser(String username);
  boolean isUserExists(String username);
}
