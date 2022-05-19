package ru.shanalotte.bankbarrel.serviceregistry.dto;

import io.swagger.v3.oas.annotations.Parameter;
import javax.validation.constraints.NotNull;

/**
 * DTO с информацией о запросе на удаление сервиса.
 */
public class ServiceDeletionDto {
  @Parameter(description = "Microservice name for deletion")
  @NotNull
  private String name;

  public String getName() {
    return name;
  }

}
