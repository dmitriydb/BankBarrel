package ru.shanalotte.bankbarrel.webapp.jwt;

import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.webapp.service.jwt.JwtTokenObtainer;
import ru.shanalotte.bankbarrel.webapp.service.jwt.JwtTokenStorer;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceUrlBuilder;

@ActiveProfiles("production")
@SpringBootTest
@AutoConfigureMockMvc
public class JwtTokenObtainerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RestTemplate restTemplate;

  @Autowired
  private JwtTokenStorer jwtTokenStorer;

  @MockBean
  private ServiceUrlBuilder serviceUrlBuilder;

  @Autowired
  private JwtTokenObtainer jwtTokenObtainer;

  @Test
  public void shouldObtainToken() throws InterruptedException {
    Map<String, String> response = new HashMap<>();
    when(serviceUrlBuilder.buildServiceUrl(any())).thenReturn("someurl");
    response.put("token", "fake");
    when(restTemplate.postForEntity(any(), any(), any())).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));
    jwtTokenObtainer.createToken();
    assertThat(jwtTokenStorer.getToken()).isEqualTo("fake");
  }

}