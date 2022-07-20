package ru.shanalotte.bankbarrel.webapp.monitoring;

import javax.sql.DataSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import static org.assertj.core.api.Assertions.assertThat;
import org.checkerframework.checker.units.qual.A;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.shanalotte.bankbarrel.webapp.controller.enroll.EnrollController;
import ru.shanalotte.bankbarrel.webapp.controller.login.LoginController;
import ru.shanalotte.bankbarrel.webapp.dto.bankclient.BankClientInfoDto;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.listener.AppContextListener;
import ru.shanalotte.bankbarrel.webapp.service.jwt.JwtTokenObtainer;
import ru.shanalotte.bankbarrel.webapp.service.monitoring.activity.UserActivityMonitoringService;

@ActiveProfiles("production")
@SpringBootTest
@AutoConfigureMockMvc
public class UserActivityMonitoringTest {

  @Autowired
  private MockMvc mockMvc;
  @SpyBean
  private UserActivityMonitoringService userActivityMonitoringService;

  @Autowired
  private LoginController loginController;

  @Autowired
  private EnrollController enrollController;


  @Test
  public void shouldMonitorLogin() {
    try {
      loginController.processLogin(null, "test");
    } catch (WebAppUserNotFound e) {
    }
    verify(userActivityMonitoringService).auditLogin("test");
  }

  @Test
  public void shouldMonitorEnroll() throws WebAppUserNotFound {
    BankClientInfoDto bankClientInfoDto = new BankClientInfoDto();
    bankClientInfoDto.setUsername("test2");
    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
    try {
      enrollController.processEnroll(null, bankClientInfoDto, null);
    } catch (Throwable t) {
    }
    verify(userActivityMonitoringService).auditEnroll(captor.capture());
    assertThat(captor.getValue().contains("test2"));
  }
}
