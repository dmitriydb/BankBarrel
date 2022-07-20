package ru.shanalotte.bankbarrel.rest.infomodule.jwt;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.serviceregistry.DeployedMicroserviceWhereAboutInformation;
import ru.shanalotte.bankbarrel.rest.infomodule.service.serviceregistry.ServiceRegistryProxy;
import ru.shanalotte.bankbarrel.rest.infomodule.service.serviceregistry.ServiceUrlBuilder;

/**
 * Этот класс должен авторизовывать данный микросервис в JWT Provider
 * для использования токена при запросах в appserver в продакшне.
 */
@Component
@Profile({"production"})
@PropertySource("classpath:jwt-credentials.properties")
public class JwtTokenObtainer {

  private static final Logger logger = LoggerFactory.getLogger(JwtTokenObtainer.class);

  @Value("${apiPassword}")
  private String password;

  @Value("${service.name}")
  private String serviceName;

  private JwtTokenStorer jwtTokenStorer;
  private ServiceUrlBuilder serviceUrlBuilder;
  private ServiceRegistryProxy serviceRegistryProxy;
  private RestTemplate restTemplate;

  @Autowired
  public JwtTokenObtainer(JwtTokenStorer jwtTokenStorer,
                          ServiceUrlBuilder serviceUrlBuilder,
                          ServiceRegistryProxy serviceRegistryProxy,
                          RestTemplate restTemplate) {
    this.jwtTokenStorer = jwtTokenStorer;
    this.serviceUrlBuilder = serviceUrlBuilder;
    this.serviceRegistryProxy = serviceRegistryProxy;
    this.restTemplate = restTemplate;
  }

  @Scheduled(initialDelay = 10000, fixedDelay = 100000)
  public void obtainTokenOnStartup() {
    DeployedMicroserviceWhereAboutInformation jwtProviderWhereAbouts =
        serviceRegistryProxy.getJwtProviderInfo();
    String urlForObtainingJwtToken =
        serviceUrlBuilder.buildServiceUrl(jwtProviderWhereAbouts) + "/auth";
    JwtCredentials credentials = new JwtCredentials(serviceName, password);
    try {
      String jwtToken = obtainToken(urlForObtainingJwtToken, credentials);
      logger.info("Obtained JWT token");
      jwtTokenStorer.setToken(jwtToken);
    } catch (URISyntaxException e) {
      logger.error("Couldn't obtain JWT token from JWT provider", e);
    }
  }

  private String obtainToken(String url, JwtCredentials credentials) throws URISyntaxException {
    ResponseEntity<Map> response = restTemplate.postForEntity(new URI(url), credentials, Map.class);
    return (String) response.getBody().get("token");
  }

  static class JwtCredentials {
    final String username;
    final String password;

    public JwtCredentials(String username, String password) {
      this.username = username;
      this.password = password;
    }

    public String getUsername() {
      return username;
    }

    public String getPassword() {
      return password;
    }
  }

}
