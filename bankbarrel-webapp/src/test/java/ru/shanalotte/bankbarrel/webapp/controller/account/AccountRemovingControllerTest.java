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
import ru.shanalotte.bankbarrel.webapp.controller.EnrollingHelper;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.account.BankAccountDto;
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

  @Test
  public void shouldNotShowDeletedAccountAfterDeletion() throws Exception {
    String username = "user153328042022";
    enrollingHelper.enrollUser(username);
    accountHelper.openThreeAccountsForUser(username);
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/" + username))
        .andReturn();
    List<BankAccountDto> dtos = (List<BankAccountDto>) mvcResult.getModelAndView().getModel().get("accounts");
    assertThat(dtos.size()).isEqualTo(3);
    BankAccountDto dto = dtos.iterator().next();
    String accountNumber = dto.getNumber();
    mockMvc.perform(MockMvcRequestBuilders.post("/account/" + accountNumber + "/delete")
        .param("username", username))
        .andDo(MockMvcResultHandlers.print());
    mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/" + username))
        .andReturn();
    dtos = (List<BankAccountDto>) mvcResult.getModelAndView().getModel().get("accounts");
    assertThat(dtos.size()).isEqualTo(2);
    assertThat(dtos.stream().anyMatch(dt -> dt.getNumber().equals(accountNumber))).isFalse();
  }

  @Test
  public void shouldRemoveAccountFromClientWhenDeleting() throws Exception {
    String username = "154428042022";
    enrollingHelper.enrollUser(username);
    accountHelper.openThreeAccountsForUser(username);
    WebAppUser webAppUser = webAppUserDao.findByUsername(username);
    BankClient client = webAppUser.getClient();
    String accountNumber = client.getAccounts().iterator().next().getIdentifier();
    assertThat(client.getAccounts()).hasSize(3);
    mockMvc.perform(MockMvcRequestBuilders.post("/account/" + accountNumber + "/delete")
            .param("username", username));
    assertThat(client.getAccounts()).hasSize(2);
    assertThat(client.getAccounts().stream()
        .anyMatch(acc -> acc.getIdentifier().equals(accountNumber))).isFalse();
  }
  
  @Test
  public void afterAccountRemovingItsPageIsUnaccessible() throws Exception {
    String username = "154828042022";
    enrollingHelper.enrollUser(username);
    accountHelper.openThreeAccountsForUser(username);
    WebAppUser webAppUser = webAppUserDao.findByUsername(username);
    BankClient client = webAppUser.getClient();
    String accountNumber = client.getAccounts().iterator().next().getIdentifier();
    mockMvc.perform(MockMvcRequestBuilders.post("/account/" + accountNumber + "/delete")
        .param("username", username));

    mockMvc.perform(MockMvcRequestBuilders.get("/user/154828042022/account/" + accountNumber))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}