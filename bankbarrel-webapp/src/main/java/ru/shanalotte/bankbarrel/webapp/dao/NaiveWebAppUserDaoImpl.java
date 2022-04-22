package ru.shanalotte.bankbarrel.webapp.dao;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

@Repository
public class NaiveWebAppUserDaoImpl implements WebAppUserDao {
  private Set<WebAppUser> existingUsers = new HashSet<>();

  @Override
  public WebAppUser findByUsername(String username) {
    return existingUsers.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
  }

  @Override
  public void addUser(String username) {
    existingUsers.add(new WebAppUser(username));
  }

  @Override
  public boolean isUserExists(String username) {
    return findByUsername(username) != null;
  }
}