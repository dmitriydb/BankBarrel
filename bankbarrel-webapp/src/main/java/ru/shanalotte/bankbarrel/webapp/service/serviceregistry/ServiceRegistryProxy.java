package ru.shanalotte.bankbarrel.webapp.service.serviceregistry;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.webapp.dto.serviceregistry.RegisteredServiceInfo;

@Service
@Profile({"production", "dev"})
public class ServiceRegistryProxy implements IServiceRegistryProxy {

  private Map<String, RegisteredServiceInfo> registeredServices = new HashMap<>();

  @Value("${services.dependencies}")
  private List<String> serviceDependencies;

  @Value("${service.registry.url}")
  private String serviceRegistryUrl;

  @Scheduled(initialDelay = 100, fixedDelay = Integer.MAX_VALUE)
  public void loadServicesInfo() {
    RestTemplate restTemplate = new RestTemplate();
    for (String serviceName : serviceDependencies) {
      String url = serviceRegistryUrl + "/" + serviceName;
      ResponseEntity<RegisteredServiceInfo> result = restTemplate.getForEntity(URI.create(url), RegisteredServiceInfo.class);
      registeredServices.put(serviceName, result.getBody());
    }
  }

  public RegisteredServiceInfo getRestInfoModuleInfo() {
    return registeredServices.get("bb-rest-infomodule");
  }

  public RegisteredServiceInfo getWebApiInfo() {
    return registeredServices.get("bb-gateway-webapi");
  }

}
