package ru.shanalotte.bankbarrel.webapp.controller.transfer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.webapp.controller.EnrollingHelper;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.testutils.AccountHelper;

@SpringBootTest
@AutoConfigureMockMvc
public class TransferControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private EnrollingHelper enrollingHelper;

  @Autowired
  private AccountHelper accountHelper;

  @Autowired
  private WebAppUserDao webAppUserDao;

  @Test
  public void shouldPassTransferDtoWhenOpeningAccountPage() throws Exception {
    enrollingHelper.enrollTestUser();
    accountHelper.openThreeAccountsForUser("testuser");
    String someAccountNumber = webAppUserDao.findByUsername("testuser").getClient().getAccounts().iterator().next().getIdentifier();
    mockMvc.perform(MockMvcRequestBuilders.get("/user/testuser/account/" + someAccountNumber))
        .andExpect(MockMvcResultMatchers.model().attributeExists("transferDto"));
  }
}