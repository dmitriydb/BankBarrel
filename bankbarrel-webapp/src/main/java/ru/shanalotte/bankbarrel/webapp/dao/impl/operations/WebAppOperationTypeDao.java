package ru.shanalotte.bankbarrel.webapp.dao.impl.operations;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperationStatus;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperationType;

@Repository
public class WebAppOperationTypeDao {

  private JdbcTemplate jdbcTemplate;

  public WebAppOperationTypeDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<WebAppOperationType> findAll() {
    return jdbcTemplate.query("SELECT * FROM webapp_operation_type",
        (rs, rowNum) -> {
          WebAppOperationType type = new WebAppOperationType();
          type.setName(rs.getString("name"));
          return type;
        }
    );
  }

  public Optional<WebAppOperationType> findByName(String name) {
    WebAppOperationType webAppOperationType = jdbcTemplate.queryForObject(
        "SELECT * FROM webapp_operation_type where name = '" + name + "'",
        (rs, rowNum) -> {
          WebAppOperationType type = new WebAppOperationType();
          type.setName(rs.getString("name"));
          return type;
        }
        );
    return Optional.of(webAppOperationType);
  }
}
