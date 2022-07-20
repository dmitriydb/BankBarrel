package ru.shanalotte.bankbarrel.webapp.service.jwt;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.serviceregistry.DeployedMicroserviceWhereAboutInformation;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceRegistryProxy;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceUrlBuilder;

@Component
@Profile({"dev", "production"})
@PropertySource("classpath:jwt-credentials.properties")
public class JwtTokenObtainer {

  @Value("${bb-webapp}")
  private String password;

  private JwtTokenStorer jwtTokenStorer;
  private ServiceRegistryProxy serviceRegistryProxy;
  private ServiceUrlBuilder serviceUrlBuilder;
  private RestTemplate restTemplate;

  @Autowired
  public JwtTokenObtainer(JwtTokenStorer jwtTokenStorer,
                          ServiceRegistryProxy serviceRegistryProxy,
                          ServiceUrlBuilder serviceUrlBuilder,
                          RestTemplate restTemplate) {
    this.jwtTokenStorer = jwtTokenStorer;
    this.serviceRegistryProxy = serviceRegistryProxy;
    this.serviceUrlBuilder = serviceUrlBuilder;
    this.restTemplate = restTemplate;
  }

  @Scheduled(initialDelay = 10000, fixedDelay = 100000)
  public void createToken() {
    DeployedMicroserviceWhereAboutInformation deployedMicroserviceWhereAboutInformation =
        serviceRegistryProxy.getJwtProviderInfo();
    String url =
        serviceUrlBuilder.buildServiceUrl(deployedMicroserviceWhereAboutInformation) + "/auth";

    class AuthDto {
      String username;
      String password;

      public AuthDto(String username, String password) {
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


    AuthDto authDto = new AuthDto("bb-webapp", password);

    try {
      System.out.println(url);
      ResponseEntity<Map> token = restTemplate.postForEntity(new URI(url), authDto, Map.class);
      System.out.println("Obtained token" + token.getBody().get("token"));
      jwtTokenStorer.setToken((String) token.getBody().get("token"));
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

}
