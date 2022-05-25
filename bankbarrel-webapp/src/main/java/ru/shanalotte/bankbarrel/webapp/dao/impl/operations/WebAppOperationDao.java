package ru.shanalotte.bankbarrel.webapp.dao.impl.operations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperation;
import ru.shanalotte.bankbarrel.webapp.entities.rowmappers.WebAppOperationRowMapper;

@Repository
public class WebAppOperationDao {

  private JdbcTemplate jdbcTemplate;

  public WebAppOperationDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void createOperation(WebAppOperation operation) {
    jdbcTemplate.update("INSERT INTO webapp_operation (init_user, operation_type, operation_status, "
            + "finished, startTs, finishedTs, requestJson) VALUES (?, ?, ?, ?, ?, ?, ?)",
        operation.getInitUser(), operation.getType(), operation.getStatus(), operation.getFinishedTime(),
        operation.getStartTime(), operation.getFinishedTime(), operation.getJson());
  }

  public Long getId(WebAppOperation webAppOperation) {
    try {
      WebAppOperation operation = jdbcTemplate.queryForObject("SELECT * FROM webapp_operation where operation_type = ? "
              + "AND operation_status = ? AND startTs = ? ", new WebAppOperationRowMapper(),
          webAppOperation.getType(), webAppOperation.getStatus(), webAppOperation.getStartTime());
      System.out.println("Found " + operation);
      return operation.getId();
    } catch (EmptyResultDataAccessException exception) {
      System.out.println("Not found anything");
      return null;
    }
  }

  public Optional<WebAppOperation> findById(Long id) {
    WebAppOperation operation = jdbcTemplate.queryForObject(
        "SELECT * FROM webapp_operation where operation_id = " + id,
        new WebAppOperationRowMapper());
    return Optional.of(operation);
  }

  public void updateOperationStatus(Long id, String newStatus) {
    jdbcTemplate.update("UPDATE webapp_operation SET operation_status = ? WHERE operation_id = ?",
        newStatus, id);
  }

  public void finishOperation(Long id) {
    Timestamp now = Timestamp.valueOf(LocalDateTime.now());
    jdbcTemplate.update("UPDATE webapp_operation SET finishedTs = ?, "
        + "finished = true WHERE operation_id = ?", now, id);
  }

}
