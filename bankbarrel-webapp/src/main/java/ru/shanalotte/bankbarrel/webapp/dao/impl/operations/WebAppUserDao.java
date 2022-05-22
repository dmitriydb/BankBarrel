package ru.shanalotte.bankbarrel.webapp.dao.impl.operations;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperation;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

@Repository
public class WebAppUserDao {

  /**
   * id serial primary key,
   * username varchar(255) unique not null,
   * registration_ts timestamp not null,
   * last_login_ts timestamp,
   * client_id integer
   */

  private JdbcTemplate jdbcTemplate;

  public WebAppUserDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void createUser(WebAppUser webAppUser) {
    jdbcTemplate.update("INSERT INTO webapp_user (username, registration_ts, "
            + "last_login_ts, client_id) VALUES (?, ?, ?, ?)",
        webAppUser.getUsername(), webAppUser.getRegistrationTs(),
        webAppUser.getLastLoginTs(), webAppUser.getBankClient().getId()
        );
  }
}
