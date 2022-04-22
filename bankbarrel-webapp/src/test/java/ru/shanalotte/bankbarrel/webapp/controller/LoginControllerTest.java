package ru.shanalotte.bankbarrel.webapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.shanalotte.bankbarrel.webapp.dao.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @SpyBean
  private WebAppUserDao webAppUserDao;

  @Test
  public void afterLoginShouldCreateWebAppUserWithTheSameName() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/login")
        .param("username", "OZSM"));
    WebAppUser webAppUser = webAppUserDao.findByUsername("OZSM");
    assertThat(webAppUser).isNotNull();
  }

  @Test
  public void afterLoginWithExistingUsernameShouldNotCreateAnotherOneWebAppUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/login")
        .param("username", "NotExisting"));
    mockMvc.perform(MockMvcRequestBuilders.post("/login")
        .param("username", "NotExisting"));
    verify(webAppUserDao,times(1)).addUser("NotExisting");
  }

}