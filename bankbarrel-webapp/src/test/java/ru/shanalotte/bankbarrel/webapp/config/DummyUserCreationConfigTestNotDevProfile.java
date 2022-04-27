package ru.shanalotte.bankbarrel.webapp.config;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.shanalotte.bankbarrel.webapp.dao.BankClientDao;
import ru.shanalotte.bankbarrel.webapp.dao.WebAppUserDao;

@ActiveProfiles("production")
@SpringBootTest
@AutoConfigureMockMvc
public class DummyUserCreationConfigTestNotDevProfile {

  @Autowired
  private WebAppUserDao webAppUserDao;

  @Autowired
  private BankClientDao bankClientDao;

  @Test
  public void shouldCreateDummyUserWhenDevProfileIsDisabled() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertThat(webAppUserDao.isUserExists("admin")).isFalse();
    assertThat(bankClientDao.findByGivenName("Admin")).isNull();
  }

}