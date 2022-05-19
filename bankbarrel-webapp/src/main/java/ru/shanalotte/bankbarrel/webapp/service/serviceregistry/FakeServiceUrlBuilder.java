package ru.shanalotte.bankbarrel.webapp.service.serviceregistry;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.dto.serviceregistry.RegisteredServiceInfo;

/**
 * Сервис, который конструирует URL для обращения к API микросервиса.
 * Во время разработки и тестирования хост из докера заменяется на localhost.
 */
@Service
@Profile({"dev", "test"})
public class FakeServiceUrlBuilder implements ServiceUrlBuilder {

  public String buildServiceUrl(RegisteredServiceInfo info) {
    String url = "http://localhost:" + info.getPort();
    return url;
  }
}

