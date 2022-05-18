package ru.shanalotte.bankbarrel.webapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankClientDao;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginAndEnrollTest {

  @Autowired
  private MockMvc mockMvc;

  @SpyBean
  private WebAppUserDao webAppUserDao;

  @Autowired
  private MessageSource messageSource;

  @SpyBean
  private BankClientDao bankClientDao;

  @Autowired
  private EnrollingHelper enrollingHelper;





  @Test
  public void shouldShowErrorWhenTryingToSeeUserPageWithNotExistingUSer() throws Exception {
    MvcResult result = mockMvc.perform(get("/user/IAMNOTEXIST"))
        .andExpect(status().isUnauthorized())
        .andReturn();
  }



  @Test
  public void shouldCreateNewBankClientAfterNewLogin() throws Exception {
    int clientsQtyBefore = bankClientDao.count();
    mockMvc.perform(post("/enroll")
        .param("username", "FullPledgeClient")
        .param("firstName", "a")
        .param("lastName", "a")
        .param("email", "a@xcyz")
    );
    int clientsQtyAfter = bankClientDao.count();
    assertThat(clientsQtyAfter - clientsQtyBefore).isEqualTo(1);
  }





  @Test
  public void cantLoginBeforeEnroll() throws Exception {
    mockMvc.perform(post("/login")
            .param("username", "I_SURE_CANT_LOGIN_YET"))
        .andExpect(status().isUnauthorized());
  }



  @Test
  public void shouldNotCreateWebAppUserAndBankClientWhenEnrollingWithExistingUsername() throws Exception {
    int usersBefore = webAppUserDao.count();
    int customersBefore = bankClientDao.count();

    mockMvc.perform(post("/enroll")
        .param("username", "FullPledgeClient5")
        .param("firstName", "FullPledgeClient5name")
        .param("lastName", "a")
        .param("email", "a@xcyz")
    );

    int usersAfter = webAppUserDao.count();
    ;
    int customersAfter = bankClientDao.count();
    assertThat(usersAfter - usersBefore).isEqualTo(1);
    assertThat(customersAfter - customersBefore).isEqualTo(1);

    //enrolling with the same username again

    mockMvc.perform(post("/enroll")
            .param("username", "FullPledgeClient5")
            .param("firstName", "FullPledgeClient5name")
            .param("lastName", "a")
            .param("email", "a@xcyz")
        )
        .andExpect(MockMvcResultMatchers.flash().attribute("message", containsString("already exists")))
        .andExpect(MockMvcResultMatchers.flash().attributeExists("dto"));

    int usersAfterAfter = webAppUserDao.count();
    int customersAfterAfter = bankClientDao.count();

    assertThat(usersAfterAfter - usersAfter).isEqualTo(0);
    assertThat(customersAfterAfter - customersAfter).isEqualTo(0);
  }

  @Test
  public void dtoValidationTestOnEnrollProcess() throws Exception {
    mockMvc.perform(post("/enroll")
        .param("username", "a132")
        .param("firstName", "")
        .param("lastName", "a")
        .param("email", "a@xcyz")
    ).andExpect(MockMvcResultMatchers.model().hasErrors());

    mockMvc.perform(post("/enroll")
        .param("username", "a132")
        .param("firstName", "a")
        .param("lastName", "")
        .param("email", "a@xcyz")
    ).andExpect(MockMvcResultMatchers.model().hasErrors());

    mockMvc.perform(post("/enroll")
        .param("username", "a132")
        .param("firstName", "a")
        .param("lastName", "b")
    ).andExpect(MockMvcResultMatchers.model().hasErrors());
  }

}