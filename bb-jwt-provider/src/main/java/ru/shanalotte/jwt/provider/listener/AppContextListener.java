package ru.shanalotte.jwt.provider.listener;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.serviceregistry.DeployedMicroserviceWhereAboutInformation;

/**
 * Листенер, который срабатывает в момент поднятия приложения.
 * Отправляет информацию о приложении в Service Registry.
 */
@Component
@Profile("production")
public class AppContextListener implements ApplicationListener<ContextRefreshedEvent> {

  private static final Logger logger = LoggerFactory.getLogger(AppContextListener.class);

  @Value("${service.name}")
  private String serviceName;
  @Value("${server.port}")
  private String port;
  @Value("${service.registry.url}")
  private String serviceRegistryUrl;

  private RestTemplate restTemplate;

  @Autowired
  public AppContextListener(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    DeployedMicroserviceWhereAboutInformation serviceInfo =
        new DeployedMicroserviceWhereAboutInformation();
    serviceInfo.setName(serviceName);
    serviceInfo.setPort(port);
    try {
      serviceInfo.setHost(InetAddress.getLocalHost().getHostName());
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    restTemplate.postForObject(URI.create(serviceRegistryUrl), serviceInfo, String.class);
  }
}
