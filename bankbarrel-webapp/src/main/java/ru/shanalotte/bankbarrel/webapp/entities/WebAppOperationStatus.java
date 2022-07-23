package ru.shanalotte.bankbarrel.webapp.entities;

public class WebAppOperationStatus {

  private Long id;
  private String operationType;
  private String status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOperationType() {
    return operationType;
  }

  public void setOperationType(String operationType) {
    this.operationType = operationType;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("WebAppOperationStatus{");
    sb.append("id=").append(id);
    sb.append(", operationType='").append(operationType).append('\'');
    sb.append(", status='").append(status).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
