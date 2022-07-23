package ru.shanalotte.bankbarrel.webapp.controller.account;


import java.util.List;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.controller.EnrollingHelper;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankClientDao;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.account.BankAccountWebAppDto;
import ru.shanalotte.bankbarrel.webapp.testutils.AccountHelper;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountRemovingControllerTest {

  @Autowired
  private WebAppUserDao webAppUserDao;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private EnrollingHelper enrollingHelper;

  @Autowired
  private AccountHelper accountHelper;

  @Autowired
  private BankClientDao bankClientDao;

  @Test
  public void shouldNotShowDeletedAccountAfterDeletion() throws Exception {
    String username = "user153328042022";
    enrollingHelper.enrollUser(username);
    accountHelper.openThreeAccountsForUser(username);
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/" + username))
        .andReturn();
    List<BankAccountWebAppDto> dtos = (List<BankAccountWebAppDto>) mvcResult.getModelAndView().getModel().get("accounts");
    assertThat(dtos.size()).isEqualTo(3);
    BankAccountWebAppDto dto = dtos.iterator().next();
    String accountNumber = dto.getNumber();
    mockMvc.perform(MockMvcRequestBuilders.post("/account/" + accountNumber + "/delete")
        .param("username", username))
        .andDo(MockMvcResultHandlers.print());
    mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/" + username))
        .andReturn();
    dtos = (List<BankAccountWebAppDto>) mvcResult.getModelAndView().getModel().get("accounts");
    assertThat(dtos.size()).isEqualTo(2);
    assertThat(dtos.stream().anyMatch(dt -> dt.getNumber().equals(accountNumber))).isFalse();
  }

  @Test
  public void shouldRemoveAccountFromClientWhenDeleting() throws Exception {
    String username = "154428042022";
    enrollingHelper.enrollUser(username);
    accountHelper.openThreeAccountsForUser(username);
    WebAppUser webAppUser = webAppUserDao.findByUsername(username);
    BankClientDto client = webAppUser.getClient();
    String accountNumber = bankClientDao.accounts(client).iterator().next().getNumber();
    assertThat(bankClientDao.accounts(client)).hasSize(3);
    mockMvc.perform(MockMvcRequestBuilders.post("/account/" + accountNumber + "/delete")
            .param("username", username));
    assertThat(bankClientDao.accounts(client)).hasSize(2);
    assertThat(bankClientDao.accounts(client).stream()
        .anyMatch(acc -> acc.getNumber().equals(accountNumber))).isFalse();
  }
  
  @Test
  public void afterAccountRemovingItsPageIsUnaccessible() throws Exception {
    String username = "154828042022";
    enrollingHelper.enrollUser(username);
    accountHelper.openThreeAccountsForUser(username);
    WebAppUser webAppUser = webAppUserDao.findByUsername(username);
    BankClientDto client = webAppUser.getClient();
    String accountNumber = bankClientDao.accounts(client).iterator().next().getIdentifier();
    mockMvc.perform(MockMvcRequestBuilders.post("/account/" + accountNumber + "/delete")
        .param("username", username));

    mockMvc.perform(MockMvcRequestBuilders.get("/user/154828042022/account/" + accountNumber))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}