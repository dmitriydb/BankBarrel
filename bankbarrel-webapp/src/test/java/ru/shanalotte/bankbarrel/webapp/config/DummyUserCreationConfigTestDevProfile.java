package ru.shanalotte.bankbarrel.webapp.config;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankClientDao;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class DummyUserCreationConfigTestDevProfile {

  @Autowired
  private WebAppUserDao webAppUserDao;

  @Autowired
  private BankClientDao bankClientDao;

  @Test
  public void shouldCreateDummyUserWhenDevProfileIsEnabled() {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertThat(webAppUserDao.isUserExists("admin")).isTrue();
    assertThat(bankClientDao.findByGivenName("Admin")).isNotNull();
  }

}