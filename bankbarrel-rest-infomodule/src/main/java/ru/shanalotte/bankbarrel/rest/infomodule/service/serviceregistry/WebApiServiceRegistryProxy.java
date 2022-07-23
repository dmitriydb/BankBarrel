package ru.shanalotte.bankbarrel.rest.infomodule.service.serviceregistry;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.serviceregistry.DeployedMicroserviceWhereAboutInformation;

/**
 * Класс, реализующий интерфейс прокси реестра микросервисов.
 * Использует интеграцию с REST в Web Api Gateway.
 */
@Service
@Profile({"production"})
public class WebApiServiceRegistryProxy implements ServiceRegistryProxy {

  private static final Logger logger = LoggerFactory.getLogger(WebApiServiceRegistryProxy.class);

  private Map<String, DeployedMicroserviceWhereAboutInformation> registeredServices
      = new HashMap<>();

  @Value("${services.dependencies}")
  private List<String> serviceDependencies;

  @Value("${service.registry.url}")
  private String serviceRegistryUrl;

  private RestTemplate restTemplate;

  @Autowired
  public WebApiServiceRegistryProxy(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Запускается при поднятии веб-приложения и получает информацию о всех остальных микросервисах.
   */
  @Scheduled(initialDelay = 10000, fixedDelay = Integer.MAX_VALUE)
  public void loadServicesInfo() {
    for (String serviceName : serviceDependencies) {
      String url = serviceRegistryUrl + "/" + serviceName;
      ResponseEntity<DeployedMicroserviceWhereAboutInformation> result =
          restTemplate.getForEntity(URI.create(url),
              DeployedMicroserviceWhereAboutInformation.class
          );
      logger.info("Loaded {} service deploying info", serviceName);
      registeredServices.put(serviceName, result.getBody());
    }
  }

  public DeployedMicroserviceWhereAboutInformation getWebApiInfo() {
    return registeredServices.get("bb-gateway-webapi");
  }

  @Override
  public DeployedMicroserviceWhereAboutInformation getJwtProviderInfo() {
    return registeredServices.get("bb-jwt-provider");
  }
}
