package ru.shanalotte.bankbarrel.webapp.dao.impl.operations;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("production")
class RealWebAppUserDaoTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private RealWebAppUserDao realWebAppUserDao;

  @Test
  public void savingSomeUser() {
    WebAppUser webAppUser = new WebAppUser();
    webAppUser.setUsername("abc" + LocalDateTime.now());
    webAppUser.setRegistrationTs(LocalDateTime.now());
    webAppUser.setBankClient(new BankClientDto());
    realWebAppUserDao.addUser(webAppUser);
  }

  @Test
  public void savingUserThatFindingIt() {
    WebAppUser webAppUser = new WebAppUser();
    webAppUser.setUsername("abc" + LocalDateTime.now());
    realWebAppUserDao.addUser(webAppUser);
    assertThat(realWebAppUserDao.findByUsername(webAppUser.getUsername())).isNotNull();
    assertThat(realWebAppUserDao.isUserExists(webAppUser.getUsername())).isTrue();
  }

  @Test
  public void countTest() {
    int before = realWebAppUserDao.count();
    WebAppUser webAppUser = new WebAppUser();
    webAppUser.setUsername("abc" + LocalDateTime.now());
    realWebAppUserDao.addUser(webAppUser);
    int after = realWebAppUserDao.count();
    assertThat(after - before).isOne();
  }

  @Test
  public void findingNullUserIsNull() {
    assertThat(realWebAppUserDao.isUserExists(null)).isFalse();
    assertThat(realWebAppUserDao.findByUsername(null)).isNull();

  }

  @Test
  public void savingUserWithClientNull() {
    WebAppUser webAppUser = new WebAppUser();
    webAppUser.setUsername("abc" + LocalDateTime.now());
    realWebAppUserDao.addUser(webAppUser);
  }

  @Test
  public void savingUserTwoTimes() {
    WebAppUser webAppUser = new WebAppUser();
    webAppUser.setUsername("abc" + LocalDateTime.now());
    realWebAppUserDao.addUser(webAppUser);
    assertThrows(Throwable.class, () -> {
      realWebAppUserDao.addUser(webAppUser);
    });
  }



}