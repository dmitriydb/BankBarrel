package ru.shanalotte.bankbarrel.webapp.service.serviceregistry;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.webapp.dto.serviceregistry.RegisteredServiceInfo;

@Service
@Profile({"production"})
public class ServiceUrlBuilder implements IServiceUrlBuilder {

  public String buildUrl(RegisteredServiceInfo info) {
    String url = "http://" + info.getHost() + ":" + info.getPort();
    return url;
  }
}
