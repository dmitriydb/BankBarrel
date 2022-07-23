package ru.shanalotte.jwt.provider.serviceregistry;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@ActiveProfiles("dev")
public class DeployingInfoSendingInDevTest {

  @MockBean
  private RestTemplate restTemplate;

  @Test
  public void notSendDeployingInfoInDev()  {
    verify(restTemplate, never()).postForObject(any(), any(), any());
  }
}
