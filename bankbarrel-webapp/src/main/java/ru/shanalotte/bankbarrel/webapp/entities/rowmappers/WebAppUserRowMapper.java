package ru.shanalotte.bankbarrel.webapp.entities.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperation;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

public class WebAppUserRowMapper implements RowMapper<WebAppUser> {


  /**
   * id serial primary key,
   * username varchar(255) unique not null,
   * registration_ts timestamp not null,
   * last_login_ts timestamp,
   * client_id integer
   */
  @Override
  public WebAppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
    WebAppUser webAppUser = new WebAppUser();
    webAppUser.setBankClient(new BankClientDto());
    webAppUser.setId(rs.getLong("id"));
    webAppUser.getBankClient().setId(rs.getLong("client_id"));
    webAppUser.setUsername(rs.getString("username"));
    if (rs.getTimestamp("last_login_ts") == null) {
      webAppUser.setLastLoginTs(null);
    } else {
      webAppUser.setLastLoginTs(rs.getTimestamp("last_login_ts").toLocalDateTime());
    }
    webAppUser.setRegistrationTs(rs.getTimestamp("registration_ts").toLocalDateTime());
    return webAppUser;
  }
}
