package ru.shanalotte.bankbarrel.rest.infomodule.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.serviceregistry.RegisteredServiceInfo;
import ru.shanalotte.bankbarrel.rest.infomodule.service.serviceregistry.ServiceRegistryProxy;
import ru.shanalotte.bankbarrel.rest.infomodule.service.serviceregistry.ServiceUrlBuilder;

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


  public JwtTokenObtainer(JwtTokenStorer jwtTokenStorer,
                          ServiceUrlBuilder serviceUrlBuilder,
                          ServiceRegistryProxy serviceRegistryProxy) {
    this.jwtTokenStorer = jwtTokenStorer;
    this.serviceUrlBuilder = serviceUrlBuilder;
    this.serviceRegistryProxy = serviceRegistryProxy;
  }

  @Scheduled(initialDelay = 10000, fixedDelay = 100000)
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


    AuthDto authDto = new AuthDto(serviceName, password);

    try {
      System.out.println(url);
      ResponseEntity<Map> token = new RestTemplate().postForEntity(new URI(url), authDto, Map.class);
      logger.info("Obtained token" + token.getBody().get("token"));
      jwtTokenStorer.setToken((String) token.getBody().get("token"));
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

}
