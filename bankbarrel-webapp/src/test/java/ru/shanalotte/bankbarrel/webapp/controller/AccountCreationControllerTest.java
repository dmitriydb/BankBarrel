package ru.shanalotte.bankbarrel.webapp.controller;

import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.hamcrest.Matchers;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.shanalotte.bankbarrel.core.domain.BankAccountAdditionalType;
import ru.shanalotte.bankbarrel.core.domain.BankAccountType;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.account.AccountOpeningDto;
import ru.shanalotte.bankbarrel.webapp.dto.account.BankAccountWebAppDto;
import ru.shanalotte.bankbarrel.webapp.service.BankAccountCreationService;
import ru.shanalotte.bankbarrel.webapp.testutils.TestDtoFactory;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountCreationControllerTest {


  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private EnrollingHelper enrollingHelper;

  @Autowired
  private WebAppUserDao webAppUserDao;

  @Autowired
  private BankAccountCreationService bankAccountCreationService;

  @Test
  public void shouldAssignNewBankAccountToTheClient() throws Exception {
    /*
    enrollingHelper.enrollUser("vasya615");
    WebAppUser webAppUser = webAppUserDao.findByUsername("vasya615");
    BankClientDto bankClient = webAppUser.getClient();
    assertThat(bankClient.getAccounts().size()).isZero();
    AccountOpeningDto accountOpeningDto = new AccountOpeningDto();
    accountOpeningDto.setAccountType("CHECKING");
    accountOpeningDto.setAccountAdditionalType("PREMIUM");
    accountOpeningDto.setCurrency("USD");

    bankAccountCreationService.createAccount(accountOpeningDto, bankClient);

    assertThat(bankClient.getAccounts().size()).isEqualTo(1);*/
    //TODO
  }






  @Test
  public void afterCreationAnAccountShouldRedirectToUserPage() throws Exception {
    String username = "vasya500";
    enrollingHelper.enrollUser(username);
    mockMvc.perform(MockMvcRequestBuilders.post("/account/create")
            .param("username", username)
            .param("accountType", BankAccountType.CHECKING.name())
            .param("accountAdditionalType", BankAccountAdditionalType.TRADITIONAL.name())
            .param("currency", "USD"))
        .andExpect(MockMvcResultMatchers.redirectedUrl("/user/" + username));

  }

  @Test
  public void shouldNotTryToOpenAccountForNotExistingUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/account/create")
        .param("username", "idefinitelydontexist")
        .param("accountType", BankAccountType.SAVING.name())
        .param("accountAdditionalType", BankAccountAdditionalType.REWARD.name())
        .param("currency", "USD"))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  public void shouldNotThrowExceptionWhenCreatingBankAccountWithValidTypes() throws Exception {
    enrollingHelper.enrollTestUser();
    assertDoesNotThrow(() -> {
      mockMvc.perform(MockMvcRequestBuilders.post("/account/create")
          .param("username", "testuser")
          .param("accountType", BankAccountType.SAVING.name())
          .param("accountAdditionalType", BankAccountAdditionalType.SAVINGS_ONLY.name())
          .param("currency", "USD"));
    });

  }

}