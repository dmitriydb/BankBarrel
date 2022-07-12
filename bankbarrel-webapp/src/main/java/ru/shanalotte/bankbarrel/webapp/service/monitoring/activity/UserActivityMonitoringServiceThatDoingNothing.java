package ru.shanalotte.bankbarrel.webapp.service.monitoring.activity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Profile({"dev", "test"})
@Service
public class UserActivityMonitoringServiceThatDoingNothing implements UserActivityMonitoringService {

  @Override
  public void auditEnroll(String enrollDto) {
  }

  @Override
  public void auditLogin(String username) {
  }
}
