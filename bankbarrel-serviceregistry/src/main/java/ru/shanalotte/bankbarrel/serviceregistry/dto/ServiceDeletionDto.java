package ru.shanalotte.bankbarrel.serviceregistry.dto;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Parameter;

public class ServiceDeletionDto {
  @Parameter(description = "Microservice name for deletion")
  @NotNull
  private String name;

  public String getName() {
    return name;
  }

}
