package ru.shanalotte.bankbarrel.rest.infomodule.listener;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.rest.infomodule.dto.RegisteredServiceInfo;

@Component
@Profile("production")
public class AppContextListener implements ApplicationListener<ContextRefreshedEvent> {

  @Value("${service.name}")
  private String serviceName;
  @Value("${server.port}")
  private String port;
  @Value("${service.registry.url}")
  private String serviceRegistryUrl;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    RegisteredServiceInfo serviceInfo = new RegisteredServiceInfo();
    serviceInfo.setName(serviceName);
    serviceInfo.setPort(port);
    try {
      serviceInfo.setHost(InetAddress.getLocalHost().getHostName());
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    RestTemplate restTemplate = new RestTemplate();
    String result = null;
    result = restTemplate.postForObject(URI.create(serviceRegistryUrl), serviceInfo, String.class);
    System.out.println(result);
  }
}
