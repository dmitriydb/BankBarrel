package ru.shanalotte.bankbarrel.webapp.entities.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperation;

public class WebAppOperationRowMapper implements RowMapper<WebAppOperation> {

  @Override
  public WebAppOperation mapRow(ResultSet rs, int rowNum) throws SQLException {
    WebAppOperation webAppOperation = new WebAppOperation();
    webAppOperation.setId(rs.getLong("operation_id"));
    webAppOperation.setInitUser(rs.getLong("init_user"));
    webAppOperation.setType(rs.getString("operation_type"));
    webAppOperation.setStatus(rs.getString("operation_status"));
    webAppOperation.setFinished(rs.getBoolean("finished"));
    webAppOperation.setStartTime(rs.getTimestamp("startTs"));
    webAppOperation.setStartTime(rs.getTimestamp("finishedTs"));
    webAppOperation.setJson(rs.getString("request_json"));
    return webAppOperation;
  }
}
