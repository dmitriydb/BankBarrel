package ru.shanalotte.bankbarrel.core.dto.serviceregistry;

/**
 * Класс (DTO), который хранит информацию о расположении микросервиса.
 */
public class RegisteredServiceInfo {
  private String name;
  private String host;
  private String port;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }
}
