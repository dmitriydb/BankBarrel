package ru.shanalotte.bankbarrel.monitoring.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.monitoring.dto.BankClientInfoDto;

@Service
public class WebAppActivityListener {

  private static final Logger logger = LoggerFactory.getLogger(WebAppActivityListener.class);

  @JmsListener(destination = "enrolls")
  public void processEnrollEvent(String content) throws JsonProcessingException {
    BankClientInfoDto dto = new ObjectMapper().readValue(content, BankClientInfoDto.class);
    logger.debug("Incoming enroll attempt dto {}", dto);
  }

  @JmsListener(destination = "logins")
  public void processLoginEvent(String username) {
    logger.debug("Incoming login attempt dto {}", username);
  }

}
