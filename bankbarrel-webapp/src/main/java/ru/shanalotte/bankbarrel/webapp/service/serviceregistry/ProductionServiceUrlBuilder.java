package ru.shanalotte.bankbarrel.webapp.service.serviceregistry;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.dto.serviceregistry.RegisteredServiceInfo;

/**
 * Реализация сервиса построения URL в продуктивной среде.
 */
@Service
@Profile({"production"})
public class ProductionServiceUrlBuilder implements ServiceUrlBuilder {

  public String buildServiceUrl(RegisteredServiceInfo info) {
    String url = "http://" + info.getHost() + ":" + info.getPort();
    return url;
  }
}
