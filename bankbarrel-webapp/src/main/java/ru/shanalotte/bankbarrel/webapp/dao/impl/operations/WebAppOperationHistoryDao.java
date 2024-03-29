package ru.shanalotte.bankbarrel.webapp.dao.impl.operations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperation;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperationHistory;
import ru.shanalotte.bankbarrel.webapp.entities.rowmappers.WebAppOperationHistoryRowMapper;
import ru.shanalotte.bankbarrel.webapp.entities.rowmappers.WebAppOperationRowMapper;

@Repository
public class WebAppOperationHistoryDao {

  private JdbcTemplate jdbcTemplate;

  public WebAppOperationHistoryDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void createEntry(WebAppOperationHistory entry) {
    jdbcTemplate.update("INSERT INTO webapp_operation_history "
            + "(operation_id, startTs, status) VALUES (?, ?, ?)",
        entry.getOperationId(), entry.getStartTs(), entry.getStatus()
    );
  }

  public void createAndCloseEntry(WebAppOperationHistory entry) {
    jdbcTemplate.update("INSERT INTO webapp_operation_history "
            + "(operation_id, startTs, status, finishedTs) VALUES (?, ?, ?, ?)",
        entry.getOperationId(), entry.getStartTs(), entry.getStatus(),
        Timestamp.valueOf(LocalDateTime.now())
    );
  }

  public Long getId(WebAppOperationHistory entry) {
    try {
      WebAppOperationHistory foundEntry = jdbcTemplate.queryForObject(
          "SELECT * FROM webapp_operation_history where operation_id = ? "
              + "AND startTs = ? AND status = ?", new WebAppOperationHistoryRowMapper(),
          entry.getOperationId(), entry.getStartTs(), entry.getStatus());
      return foundEntry.getId();
    } catch (EmptyResultDataAccessException exception) {
      return null;
    }
  }

  public Optional<WebAppOperationHistory> findById(Long id) {
    WebAppOperationHistory entry = jdbcTemplate.queryForObject(
        "SELECT * FROM webapp_operation_history where id = " + id,
        new WebAppOperationHistoryRowMapper());
    return Optional.of(entry);
  }

  public void closeEntry(Long id) {
    Timestamp now = Timestamp.valueOf(LocalDateTime.now());
    jdbcTemplate.update("UPDATE webapp_operation_history SET finishedTs = ? WHERE id = ?",
        now, id);
  }


}
