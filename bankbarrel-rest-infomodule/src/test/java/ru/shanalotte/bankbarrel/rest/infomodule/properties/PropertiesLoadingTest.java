package ru.shanalotte.bankbarrel.rest.infomodule.properties;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.test.context.ActiveProfiles;
import ru.shanalotte.bankbarrel.rest.infomodule.listener.MicroserviceRegistryDeployingInfoSender;

@ActiveProfiles("production")
@SpringBootTest
public class PropertiesLoadingTest {

  @MockBean
  MicroserviceRegistryDeployingInfoSender microserviceRegistryDeployingInfoSender;

  @MockBean
  JedisConnectionFactory jedisConnectionFactory;

  @Value("${redis.host}")
  private String redisHost;

  @Value("${redis.port}")
  private int redisPort;

  @Value("${apiPassword}")
  private String password;

  @Value("${service.name}")
  private String serviceName;

  @Value("${jwt.token.secret}")
  private String secret;

  @Value("${jwt.validClaims}")
  private List<String> validClaims;

  @Value("${server.port}")
  private String port;
  @Value("${service.registry.url}")
  private String serviceRegistryUrl;

  @Test
  public void propertiesAreLoaded() {
    assertThat(redisHost).isNotNull();
    assertThat(redisPort).isNotNull();
    assertThat(password).isNotNull();
    assertThat(serviceName).isNotNull();
    assertThat(secret).isNotNull();
    assertThat(validClaims).isNotNull();
    assertThat(port).isNotNull();
    assertThat(serviceRegistryUrl).isNotNull();
  }
}
