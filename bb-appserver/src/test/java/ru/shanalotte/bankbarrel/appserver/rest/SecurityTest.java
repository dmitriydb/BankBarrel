package ru.shanalotte.bankbarrel.appserver.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

  @Autowired
  private MockMvc mockMvc;

  public void statusIs403WithoutJWTToken() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/accounts")).andExpect(MockMvcResultMatchers.status().isForbidden());
  }
}
