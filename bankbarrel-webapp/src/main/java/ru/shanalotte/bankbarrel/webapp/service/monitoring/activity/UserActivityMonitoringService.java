package ru.shanalotte.bankbarrel.webapp.service.monitoring.activity;

public interface UserActivityMonitoringService {

  void auditEnroll(String enrollDto);

  void auditLogin(String username);

}
