package ru.shanalotte.bankbarrel.rest.infomodule.service.serviceregistry;

import ru.shanalotte.bankbarrel.core.dto.serviceregistry.RegisteredServiceInfo;

/**
 * Сервис, который создает URL микросервиса по информации о его местоположении.
 */
public interface ServiceUrlBuilder {
  String buildServiceUrl(RegisteredServiceInfo info);
}
