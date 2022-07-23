package ru.shanalotte.bankbarrel.rest.infomodule.jwt;

import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.rest.infomodule.service.serviceregistry.ServiceUrlBuilder;

@ActiveProfiles("production")
@SpringBootTest
public class JwtTokenObtainerTest {

  @MockBean
  private JedisConnectionFactory jedisConnectionFactory;

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
    jwtTokenObtainer.obtainTokenOnStartup();
    assertThat(jwtTokenStorer.getToken()).isEqualTo("fake");
  }

}