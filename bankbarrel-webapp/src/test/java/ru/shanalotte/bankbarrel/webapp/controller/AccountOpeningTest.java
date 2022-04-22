package ru.shanalotte.bankbarrel.webapp.controller;

import static org.hamcrest.Matchers.containsString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountOpeningTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private EnrollingHelper enrollingHelper;

  @Test
  public void userPageModelShouldContainAccountOpeningDto() throws Exception {
    enrollingHelper.enrollSomeUser();

    mockMvc.perform(MockMvcRequestBuilders.get("/user/testuser"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("accountOpeningDto"));
  }
}
