package ru.shanalotte.bankbarrel.webapp.service.serviceregistry;

import ru.shanalotte.bankbarrel.webapp.dto.serviceregistry.RegisteredServiceInfo;

public interface IServiceUrlBuilder {
  String buildUrl(RegisteredServiceInfo info);
}
