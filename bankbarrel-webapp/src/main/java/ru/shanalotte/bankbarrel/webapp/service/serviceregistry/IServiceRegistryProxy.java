package ru.shanalotte.bankbarrel.webapp.service.serviceregistry;

import ru.shanalotte.bankbarrel.webapp.dto.serviceregistry.RegisteredServiceInfo;

public interface IServiceRegistryProxy {
  void loadServicesInfo();
  RegisteredServiceInfo getRestInfoModuleInfo();
  RegisteredServiceInfo getWebApiInfo();
}
