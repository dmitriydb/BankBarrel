package ru.shanalotte.bankbarrel.webapp.service.jwt;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.serviceregistry.RegisteredServiceInfo;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceRegistryProxy;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceUrlBuilder;

@Component
@PropertySource("classpath:jwt-credentials.properties")
public class JwtTokenObtainer {

  @Value("${bb-webapp}")
  private String password;

  private JwtTokenStorer jwtTokenStorer;
  private ServiceRegistryProxy serviceRegistryProxy;
  private ServiceUrlBuilder serviceUrlBuilder;

  public JwtTokenObtainer(JwtTokenStorer jwtTokenStorer, ServiceRegistryProxy serviceRegistryProxy, ServiceUrlBuilder serviceUrlBuilder) {
    this.jwtTokenStorer = jwtTokenStorer;
    this.serviceRegistryProxy = serviceRegistryProxy;
    this.serviceUrlBuilder = serviceUrlBuilder;
  }

  @Autowired


  @Scheduled(initialDelay = 10000L, fixedDelay = 100000)
  public void createToken() {
    RegisteredServiceInfo registeredServiceInfo = serviceRegistryProxy.getJwtProviderInfo();
    String url = serviceUrlBuilder.buildServiceUrl(registeredServiceInfo) + "/auth";

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
      ResponseEntity<Map> token = new RestTemplate().postForEntity(new URI(url), authDto, Map.class);
      System.out.println("Obtained token" + token.getBody().get("token"));
      jwtTokenStorer.setToken((String) token.getBody().get("token"));
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

}
