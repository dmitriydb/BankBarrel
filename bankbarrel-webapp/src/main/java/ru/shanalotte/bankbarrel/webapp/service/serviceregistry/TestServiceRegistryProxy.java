package ru.shanalotte.bankbarrel.webapp.service.serviceregistry;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.dto.serviceregistry.RegisteredServiceInfo;

/**
 * Заглушка для прокси Service Registry на этапе тестирования.
 */
@Service
@Profile({"test"})
public class TestServiceRegistryProxy implements ServiceRegistryProxy {

  public void loadServicesInfo() {
  }

  public RegisteredServiceInfo getRestInfoModuleInfo() {
    return null;
  }

  public RegisteredServiceInfo getWebApiInfo() {
    return null;
  }

}
