package ru.shanalotte.bankbarrel.webapp.controller;

import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class IndexPageControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void webAppUserSeesLoginInviteOnMainPage() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(containsString("Login")))
        .andExpect(MockMvcResultMatchers.content().string(containsString("Username")));
  }

  @Test
  public void enrollProcess() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(containsString("Enroll")))
        .andExpect(MockMvcResultMatchers.content().string(containsString("name")))
        .andExpect(MockMvcResultMatchers.content().string(containsString("surname")))
        .andExpect(MockMvcResultMatchers.content().string(containsString("email")))
        .andExpect(MockMvcResultMatchers.content().string(containsString("telephone")))
    ;

  }

  @Test
  public void webAppUserCanTypeUsernameAndLogin() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/enroll")
            .param("username", "Vasya")
            .param("firstName", "vasya")
            .param("lastName", "vasya")
            .param("email", "a@xcyz")
        )
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.redirectedUrl("/user/Vasya"));
  }

}