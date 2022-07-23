package ru.shanalotte.bankbarrel.webapp.entities.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperationHistory;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

public class WebAppOperationHistoryRowMapper implements RowMapper<WebAppOperationHistory> {
  @Override
  public WebAppOperationHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
    WebAppOperationHistory h = new WebAppOperationHistory();
    h.setId(rs.getLong("id"));
    h.setOperationId(rs.getLong("operation_id"));
    if (rs.getTimestamp("finishedTs") == null) {
      h.setFinishedTs(null);
    } else {
      h.setFinishedTs(rs.getTimestamp("finishedTs").toLocalDateTime());
    }
    h.setStartTs(rs.getTimestamp("startTs").toLocalDateTime());
    h.setStatus(rs.getString("status"));
    return h;
  }
}
