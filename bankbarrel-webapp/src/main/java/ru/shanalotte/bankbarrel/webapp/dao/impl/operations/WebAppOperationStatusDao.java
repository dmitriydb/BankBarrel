package ru.shanalotte.bankbarrel.webapp.dao.impl.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperationStatus;

@Repository
public class WebAppOperationStatusDao {

  private JdbcTemplate jdbcTemplate;

  public WebAppOperationStatusDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<WebAppOperationStatus> findAll() {
    return jdbcTemplate.query("SELECT * FROM webapp_operation_status",
        (rs, rowNum) -> {
          WebAppOperationStatus status = new WebAppOperationStatus();
          status.setId(rs.getLong("id"));
          status.setOperationType(rs.getString("operation_type"));
          status.setStatus(rs.getString("status"));
          return status;
        }
    );
  }

  public Optional<WebAppOperationStatus> findByStatus(String status) {
    WebAppOperationStatus webAppOperationStatus = jdbcTemplate.queryForObject(
        "SELECT * FROM webapp_operation_status where status = '" + status + "'",
        (rs, rowNum) -> {
          WebAppOperationStatus dto = new WebAppOperationStatus();
          dto.setId(rs.getLong("id"));
          dto.setOperationType(rs.getString("operation_type"));
          dto.setStatus(rs.getString("status"));
          return dto;
        }
        );
    return Optional.of(webAppOperationStatus);
  }
}
