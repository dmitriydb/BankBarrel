package ru.shanalotte.bankbarrel.webapp.service.serviceregistry;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.serviceregistry.RegisteredServiceInfo;

/**
 * Заглушка для прокси Service Registry на этапе тестирования.
 */
@Service
@Profile({"test", "dev"})
public class TestServiceRegistryProxy implements ServiceRegistryProxy {

  @Value("${services.dependencies}")
  private List<String> serviceDependencies;

  @Value("${restInfomoduleHost}")
  private String restInfomoduleHost;

  @Value("${restInfomodulePort}")
  private String restInfomodulePort;

  @Value("${jwtProviderHost}")
  private String jwtProviderHost;

  @Value("${jwtProviderPort}")
  private String jwtProviderPort;


  @Value("${service.registry.url}")
  private String serviceRegistryUrl;

  private class RegisteredServiceInfoDecorator {
    private RegisteredServiceInfo value;

    RegisteredServiceInfoDecorator(String name, String host, String port) {
      value = new RegisteredServiceInfo();
      value.setHost(host);
      value.setPort(port);
      value.setName(name);
    }

    public RegisteredServiceInfo getValue() {
      return value;
    }
  }


  public void loadServicesInfo() {


  }


  @Override
  public RegisteredServiceInfo getRestInfoModuleInfo() {
    return new RegisteredServiceInfoDecorator("bb-rest-infomodule", restInfomoduleHost, restInfomodulePort).getValue();
  }

  @Override
  public RegisteredServiceInfo getJwtProviderInfo() {
    return new RegisteredServiceInfoDecorator("bb-jwt-provider", jwtProviderHost, jwtProviderPort).getValue();
  }

  public RegisteredServiceInfo getWebApiInfo() {
    return null;
  }

}
