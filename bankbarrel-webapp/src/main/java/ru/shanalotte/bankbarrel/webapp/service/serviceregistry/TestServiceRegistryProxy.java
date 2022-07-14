package ru.shanalotte.bankbarrel.webapp.service.serviceregistry;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.dto.serviceregistry.DeployedMicroserviceWhereAboutInformation;

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
    private DeployedMicroserviceWhereAboutInformation value;

    RegisteredServiceInfoDecorator(String name, String host, String port) {
      value = new DeployedMicroserviceWhereAboutInformation();
      value.setHost(host);
      value.setPort(port);
      value.setName(name);
    }

    public DeployedMicroserviceWhereAboutInformation getValue() {
      return value;
    }
  }


  public void loadServicesInfo() {


  }


  @Override
  public DeployedMicroserviceWhereAboutInformation getRestInfoModuleInfo() {
    return new RegisteredServiceInfoDecorator("bb-rest-infomodule", restInfomoduleHost, restInfomodulePort).getValue();
  }

  @Override
  public DeployedMicroserviceWhereAboutInformation getJwtProviderInfo() {
    return new RegisteredServiceInfoDecorator("bb-jwt-provider", jwtProviderHost, jwtProviderPort).getValue();
  }

  public DeployedMicroserviceWhereAboutInformation getWebApiInfo() {
    return null;
  }

}
