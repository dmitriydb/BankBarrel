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
public class UserProfileTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private EnrollingHelper enrollingHelper;

  @Test
  public void shouldNotOpenProfileWhenUserDoesntExists() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/user/vasyaad/profile"))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  public void canOpenProfileIfAuthorized() throws Exception {
    enrollingHelper.enrollUser("vasya666");
    mockMvc.perform(MockMvcRequestBuilders.get("/user/vasya666/profile"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void profileInfoIsPresent() throws Exception {
    enrollingHelper.enrollUser("vasya667");
    mockMvc.perform(MockMvcRequestBuilders.get("/user/vasya667/profile"))
        .andExpect(MockMvcResultMatchers.content().string(containsString("vasya667")))
        .andExpect(MockMvcResultMatchers.content().string(containsString("vasya667@vasya667")));
  }
}
