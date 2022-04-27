package ru.shanalotte.bankbarrel.webapp.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.shanalotte.bankbarrel.core.domain.BankAccountAdditionalType;
import ru.shanalotte.bankbarrel.core.domain.BankAccountType;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountCreationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private EnrollingHelper enrollingHelper;

  @Test
  public void shouldThrowExceptionWhenCreatingBankAccountWithMismatchingTypes() throws Exception {
    enrollingHelper.enrollTestUser();
    assertThrows(Throwable.class, () -> {
      mockMvc.perform(MockMvcRequestBuilders.post("/account/create")
          .param("username", "testuser")
          .param("accountType", BankAccountType.SAVING.name())
          .param("accountAdditionalType", BankAccountAdditionalType.REWARDS.name())
          .param("currency", "USD"));
    });
  }

  @Test
  public void shouldNotTryToOpenAccountForNotExistingUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/account/create")
        .param("username", "idefinitelydontexist")
        .param("accountType", BankAccountType.SAVING.name())
        .param("accountAdditionalType", BankAccountAdditionalType.REWARDS.name())
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