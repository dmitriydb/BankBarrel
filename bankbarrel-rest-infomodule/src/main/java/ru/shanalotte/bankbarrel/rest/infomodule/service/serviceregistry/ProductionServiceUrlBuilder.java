package ru.shanalotte.bankbarrel.rest.infomodule.service.serviceregistry;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.dto.serviceregistry.DeployedMicroserviceWhereAboutInformation;

/**
 * Реализация сервиса построения URL в продуктивной среде.
 */
@Service
@Profile({"production"})
public class ProductionServiceUrlBuilder implements ServiceUrlBuilder {

  public String buildServiceUrl(DeployedMicroserviceWhereAboutInformation info) {
    String url = "http://" + info.getHost() + ":" + info.getPort();
    return url;
  }
}
