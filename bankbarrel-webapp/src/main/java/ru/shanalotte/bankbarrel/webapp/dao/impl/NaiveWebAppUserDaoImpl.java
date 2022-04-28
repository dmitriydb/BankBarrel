package ru.shanalotte.bankbarrel.webapp.dao.impl;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

/**
 * Заглушка для DAO пользователей веб-поиложения.
 * Хранит всё в памяти.
 */
@Repository
public class NaiveWebAppUserDaoImpl implements WebAppUserDao {
  private Set<WebAppUser> existingUsers = new HashSet<>();

  @Override
  public WebAppUser findByUsername(String username) {
    return existingUsers.stream()
        .filter(user -> user.getUsername().equals(username))
        .findFirst()
        .orElse(null);
  }

  @Override
  public void addUser(WebAppUser webAppUser) {
    existingUsers.add(webAppUser);
  }

  @Override
  public boolean isUserExists(String username) {
    return findByUsername(username) != null;
  }

  @Override
  public int count() {
    return existingUsers.size();
  }
}
