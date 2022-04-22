package ru.shanalotte.bankbarrel.webapp.controller;

import java.util.Locale;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.shanalotte.bankbarrel.webapp.dao.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @SpyBean
  private WebAppUserDao webAppUserDao;

  @Autowired
  private MessageSource messageSource;

  @Test
  public void afterLoginShouldCreateWebAppUserWithTheSameName() throws Exception {
    mockMvc.perform(post("/login")
        .param("username", "OZSM"));
    WebAppUser webAppUser = webAppUserDao.findByUsername("OZSM");
    assertThat(webAppUser).isNotNull();
  }

  @Test
  public void afterLoginWithExistingUsernameShouldNotCreateAnotherOneWebAppUser() throws Exception {
    mockMvc.perform(post("/login")
        .param("username", "NotExisting"));
    mockMvc.perform(post("/login")
        .param("username", "NotExisting"));
    verify(webAppUserDao,times(1)).addUser("NotExisting");
  }

  @Test
  public void shouldShowErrorWhenTryingToSeeUserPageWithNotExistingUSer() throws Exception {
    MvcResult result = mockMvc.perform(get("/user/IAMNOTEXIST"))
        .andExpect(status().isUnauthorized())
        .andReturn();
  }

  @Test
  public void shouldGrantAccessToAlreadyExistingUser() throws Exception {
    mockMvc.perform(post("/login")
        .param("username", "iamhere"));

    mockMvc.perform(get("/user/iamhere"))
        .andExpect(status().isOk());
  }

}