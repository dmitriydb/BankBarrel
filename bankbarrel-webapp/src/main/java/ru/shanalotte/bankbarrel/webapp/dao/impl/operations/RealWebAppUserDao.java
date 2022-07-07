package ru.shanalotte.bankbarrel.webapp.dao.impl.operations;

import java.time.LocalDateTime;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperation;
import ru.shanalotte.bankbarrel.webapp.entities.rowmappers.WebAppUserRowMapper;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

@Repository
@Profile({"production"})
public class RealWebAppUserDao implements WebAppUserDao {

  /**
   * id serial primary key,
   * username varchar(255) unique not null,
   * registration_ts timestamp not null,
   * last_login_ts timestamp,
   * client_id integer
   */

  private JdbcTemplate jdbcTemplate;

  public RealWebAppUserDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public WebAppUser findByUsername(String username) {
    try {
      WebAppUser user = jdbcTemplate.queryForObject("SELECT * FROM webapp_user WHERE username = ?", new WebAppUserRowMapper(), username);
      return user;
    } catch (EmptyResultDataAccessException ex) {
      return null;
    }
  }

  @Override
  public void addUser(WebAppUser webAppUser) {
    webAppUser.setRegistrationTs(LocalDateTime.now());
    jdbcTemplate.update("INSERT INTO webapp_user (username, registration_ts, "
            + "last_login_ts, client_id) VALUES (?, ?, ?, ?)",
        webAppUser.getUsername(), webAppUser.getRegistrationTs(),
        webAppUser.getLastLoginTs(), webAppUser.getBankClient().getId()
    );
  }

  @Override
  public boolean isUserExists(String username) {
    try {
      WebAppUser user = jdbcTemplate.queryForObject("SELECT * FROM webapp_user WHERE username = ?", new WebAppUserRowMapper(), username);
      return true;
    } catch (EmptyResultDataAccessException ex) {
      return false;
    }
  }

  @Override
  public int count() {
    return jdbcTemplate.queryForObject("SELECT count(*) FROM webapp_user", Integer.class);
  }
}
