package ru.shanalotte.bankbarrel.rest.infomodule.serviceregistry;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import com.sun.org.apache.xpath.internal.Arg;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.serviceregistry.DeployedMicroserviceWhereAboutInformation;

@SpringBootTest
@ActiveProfiles("production")
public class DeployingInfoSendingTest {

  @Value("${service.name}")
  private String serviceName;
  @Value("${server.port}")
  private String port;
  @Value("${service.registry.url}")
  private String serviceRegistryUrl;

  @MockBean
  private JedisConnectionFactory jedisConnectionFactory;

  @MockBean
  private RestTemplate restTemplate;

  @Test
  public void sendsDeployingInfo() throws UnknownHostException {
    String host = InetAddress.getLocalHost().getHostName();
    String url = "";
    ArgumentCaptor<DeployedMicroserviceWhereAboutInformation> infoCaptor = ArgumentCaptor.forClass(DeployedMicroserviceWhereAboutInformation.class);
    ArgumentCaptor<URI> uriCaptor = ArgumentCaptor.forClass(URI.class);
    verify(restTemplate).postForObject(uriCaptor.capture(), infoCaptor.capture(), any());
    assertThat(uriCaptor.getValue().getPath().contains(serviceRegistryUrl));
    assertThat(infoCaptor.getValue().getHost()).isEqualTo(host);
    assertThat(infoCaptor.getValue().getPort()).isEqualTo(port);
    assertThat(infoCaptor.getValue().getName()).isEqualTo(serviceName);
  }
}
