package ru.shanalotte.bankbarrel.webapp.entities;

import java.time.LocalDateTime;

public class WebAppOperationHistory {

  private Long id;
  private Long operationId;
  private LocalDateTime startTs;
  private LocalDateTime finishedTs;
  private String status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getOperationId() {
    return operationId;
  }

  public void setOperationId(Long operationId) {
    this.operationId = operationId;
  }

  public LocalDateTime getStartTs() {
    return startTs;
  }

  public void setStartTs(LocalDateTime startTs) {
    this.startTs = startTs;
  }

  public LocalDateTime getFinishedTs() {
    return finishedTs;
  }

  public void setFinishedTs(LocalDateTime finishedTs) {
    this.finishedTs = finishedTs;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("WebAppOperationHistory{");
    sb.append("id=").append(id);
    sb.append(", operationId=").append(operationId);
    sb.append(", startTs=").append(startTs);
    sb.append(", finishedTs=").append(finishedTs);
    sb.append(", status='").append(status).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
