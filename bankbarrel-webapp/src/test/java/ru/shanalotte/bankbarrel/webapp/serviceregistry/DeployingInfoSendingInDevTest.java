package ru.shanalotte.bankbarrel.webapp.serviceregistry;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
public class DeployingInfoSendingInDevTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RestTemplate restTemplate;

  @Test
  public void notSendDeployingInfoInDev()  {
    verify(restTemplate, never()).postForObject(any(), any(), any());
  }
}
