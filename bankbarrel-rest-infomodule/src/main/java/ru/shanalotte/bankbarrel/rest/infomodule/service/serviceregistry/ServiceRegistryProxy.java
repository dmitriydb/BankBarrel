package ru.shanalotte.bankbarrel.rest.infomodule.service.serviceregistry;

import ru.shanalotte.bankbarrel.core.dto.serviceregistry.RegisteredServiceInfo;

/**
 * Интерфейс сервиса, который загружает информацию о существующих микросервисах из
 * Service Registry и выдает информацию о конкретном микросервисе клиентам.
 */
public interface ServiceRegistryProxy {
  void loadServicesInfo();

  RegisteredServiceInfo getWebApiInfo();

  RegisteredServiceInfo getJwtProviderInfo();
}
