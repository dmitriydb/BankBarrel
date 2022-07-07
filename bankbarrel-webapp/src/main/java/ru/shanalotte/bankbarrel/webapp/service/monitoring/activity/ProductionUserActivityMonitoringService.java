package ru.shanalotte.bankbarrel.webapp.service.monitoring.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile("production")
public class ProductionUserActivityMonitoringService implements UserActivityMonitoringService {

  private JmsTemplate jmsTemplate;

  @Autowired
  public ProductionUserActivityMonitoringService(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  @Override
  public void auditEnroll(String enrollDto) {
    jmsTemplate.convertAndSend("enrolls", enrollDto);
  }

  @Override
  public void auditLogin(String username) {
    jmsTemplate.convertAndSend("logins", username);
  }
}
