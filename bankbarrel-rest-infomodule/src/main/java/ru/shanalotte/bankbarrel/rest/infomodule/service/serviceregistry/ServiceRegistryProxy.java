package ru.shanalotte.bankbarrel.rest.infomodule.service.serviceregistry;

import ru.shanalotte.bankbarrel.core.dto.serviceregistry.DeployedMicroserviceWhereAboutInformation;

/**
 * Интерфейс сервиса, который загружает информацию о существующих микросервисах из
 * Service Registry и выдает информацию о конкретном микросервисе клиентам.
 */
public interface ServiceRegistryProxy {

  DeployedMicroserviceWhereAboutInformation getWebApiInfo();

  DeployedMicroserviceWhereAboutInformation getJwtProviderInfo();
}
