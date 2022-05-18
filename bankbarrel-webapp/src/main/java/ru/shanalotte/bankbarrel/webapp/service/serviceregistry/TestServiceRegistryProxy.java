package ru.shanalotte.bankbarrel.webapp.service.serviceregistry;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.webapp.dto.serviceregistry.RegisteredServiceInfo;

@Service
@Profile({"test"})
public class TestServiceRegistryProxy implements IServiceRegistryProxy {

  public void loadServicesInfo() {
  }

  public RegisteredServiceInfo getRestInfoModuleInfo() {
    return null;
  }

  public RegisteredServiceInfo getWebApiInfo() {
    return null;
  }

}
