package ru.shanalotte.bankbarrel.rest.infomodule.service.serviceregistry;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.serviceregistry.RegisteredServiceInfo;

/**
 * Класс, реализующий интерфейс прокси реестра микросервисов.
 * Использует интеграцию с REST в Web Api Gateway.
 */
@Service
@Profile({"production"})
public class WebApiServiceRegistryProxy implements ServiceRegistryProxy {

  private static final Logger logger = LoggerFactory.getLogger(WebApiServiceRegistryProxy.class);

  private Map<String, RegisteredServiceInfo> registeredServices = new HashMap<>();

  @Value("${services.dependencies}")
  private List<String> serviceDependencies;

  @Value("${service.registry.url}")
  private String serviceRegistryUrl;

  /**
   * Запускается при поднятии веб-приложения и получает информацию о всех остальных микросервисах.
   */
  @Scheduled(initialDelay = 10000, fixedDelay = Integer.MAX_VALUE)
  public void loadServicesInfo() {
    RestTemplate restTemplate = new RestTemplate();
    for (String serviceName : serviceDependencies) {
      String url = serviceRegistryUrl + "/" + serviceName;
      ResponseEntity<RegisteredServiceInfo> result =
          restTemplate.getForEntity(URI.create(url), RegisteredServiceInfo.class);
      logger.info("Loaded {} info", serviceName);
      logger.info("{} {} {}", result.getBody().getName(), result.getBody().getHost(), result.getBody().getPort());
      registeredServices.put(serviceName, result.getBody());
    }
  }

  public RegisteredServiceInfo getWebApiInfo() {
    return registeredServices.get("bb-gateway-webapi");
  }

  @Override
  public RegisteredServiceInfo getJwtProviderInfo() {
    return registeredServices.get("bb-jwt-provider");
  }
}
