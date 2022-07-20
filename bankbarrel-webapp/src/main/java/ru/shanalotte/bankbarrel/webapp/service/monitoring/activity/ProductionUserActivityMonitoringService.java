package ru.shanalotte.bankbarrel.webapp.service.monitoring.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile("production")
public class ProductionUserActivityMonitoringService implements UserActivityMonitoringService {

  private static final Logger logger =
      LoggerFactory.getLogger(ProductionUserActivityMonitoringService.class);

  private JmsTemplate jmsTemplate;


  @Autowired
  public ProductionUserActivityMonitoringService(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  @Override
  public void auditEnroll(String enrollDto) {
    try {
      jmsTemplate.convertAndSend("enrolls", enrollDto);
    } catch (Throwable t) {
      logger.error("", t);
    }

  }

  @Override
  public void auditLogin(String username) {
    try {
      jmsTemplate.convertAndSend("logins", username);
    } catch (Throwable t) {
      logger.error("", t);
    }
  }
}
