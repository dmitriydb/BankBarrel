package ru.shanalotte.bankbarrel.serviceregistry.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "registered_services")
public class RegisteredServiceInfo {
  @Id
  @NotNull
  private String name;
  @NotNull
  private String host;
  @NotNull
  @Min(1)
  @Max(65536)
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
