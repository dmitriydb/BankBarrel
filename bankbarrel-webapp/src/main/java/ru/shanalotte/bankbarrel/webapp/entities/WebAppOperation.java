package ru.shanalotte.bankbarrel.webapp.entities;

import java.sql.Timestamp;

public class WebAppOperation {

  private Long id;
  private Long initUser;
  private String type;
  private String status;
  private boolean finished;
  private Timestamp startTime;
  private Timestamp finishedTime;
  private String json;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getInitUser() {
    return initUser;
  }

  public void setInitUser(Long initUser) {
    this.initUser = initUser;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public boolean isFinished() {
    return finished;
  }

  public void setFinished(boolean finished) {
    this.finished = finished;
  }

  public Timestamp getStartTime() {
    return startTime;
  }

  public void setStartTime(Timestamp startTime) {
    this.startTime = startTime;
  }

  public Timestamp getFinishedTime() {
    return finishedTime;
  }

  public void setFinishedTime(Timestamp finishedTime) {
    this.finishedTime = finishedTime;
  }

  public String getJson() {
    return json;
  }

  public void setJson(String json) {
    this.json = json;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("WebAppOperation{");
    sb.append("id=").append(id);
    sb.append(", initUser=").append(initUser);
    sb.append(", type='").append(type).append('\'');
    sb.append(", status='").append(status).append('\'');
    sb.append(", finished=").append(finished);
    sb.append(", startTime=").append(startTime);
    sb.append(", finishedTime=").append(finishedTime);
    sb.append(", json='").append(json).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
