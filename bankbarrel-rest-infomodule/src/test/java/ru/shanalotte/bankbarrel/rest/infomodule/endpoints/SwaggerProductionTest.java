package ru.shanalotte.bankbarrel.rest.infomodule.endpoints;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ActiveProfiles("production")
@AutoConfigureMockMvc
public class SwaggerProductionTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void swaggerIsAccessibleInDevProfile() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/swagger-ui/index.html")).andExpect(MockMvcResultMatchers.status().isOk());
  }
}
