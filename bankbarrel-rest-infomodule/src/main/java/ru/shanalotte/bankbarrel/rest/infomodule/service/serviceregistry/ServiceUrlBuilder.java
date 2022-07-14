package ru.shanalotte.bankbarrel.rest.infomodule.service.serviceregistry;

import ru.shanalotte.bankbarrel.core.dto.serviceregistry.DeployedMicroserviceWhereAboutInformation;

/**
 * Сервис, который создает URL микросервиса по информации о его местоположении.
 */
public interface ServiceUrlBuilder {
  String buildServiceUrl(DeployedMicroserviceWhereAboutInformation info);
}
