package ru.shanalotte.bankbarrel.serviceregistry.controller;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.shanalotte.bankbarrel.serviceregistry.dto.RegisteredServiceInfo;
import ru.shanalotte.bankbarrel.serviceregistry.dto.ServiceDeletionDto;
import ru.shanalotte.bankbarrel.serviceregistry.repository.RegisteredServiceRepository;

@RestController
public class ServiceRegistryController {

  private RegisteredServiceRepository registeredServiceRepository;

  public ServiceRegistryController(RegisteredServiceRepository registeredServiceRepository) {
    this.registeredServiceRepository = registeredServiceRepository;
  }

  @Operation(description = "Get registered microservices list")
  @GetMapping(value = "/services", produces = "application/json")
  public @ResponseBody
  List<RegisteredServiceInfo> registeredServicesList() {
    List<RegisteredServiceInfo> list = registeredServiceRepository.findAll();
    return list;
  }

  @ApiResponse(responseCode = "404", description = "Service with such name is not registered", content = @Content)
  @Operation(description = "Get microservice info by name")
  @GetMapping(value = "/services/{name}", produces = "application/json")
  public ResponseEntity<RegisteredServiceInfo> getServiceInfo(@Parameter(description = "Microservice name") @PathVariable("name") String serviceName) {
    RegisteredServiceInfo info = registeredServiceRepository.findByName(serviceName);
    if (info == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<RegisteredServiceInfo>(info, HttpStatus.OK);
  }

  @Operation(description = "Create microservice information")
  @PostMapping(value = "/services", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> createService(@Schema(implementation = RegisteredServiceInfo.class)
                                           @RequestBody RegisteredServiceInfo newService) {
    registeredServiceRepository.save(newService);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Operation(description = "Update existing microservice info or create new one")
  @PutMapping(value = "/services", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> updateService(@RequestBody RegisteredServiceInfo newService) {
    registeredServiceRepository.save(newService);
    return new ResponseEntity<String>(HttpStatus.OK);
  }

  @Operation(description = "Delete microservice info")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "404", description = "Service with such name is not registered", content = @Content),
      @ApiResponse(responseCode = "200", description = "Service with such name was found AND successfully deleted", content = @Content)
  })
  @DeleteMapping(value = "/services", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> deleteService(@RequestBody ServiceDeletionDto deletionDto) {
    RegisteredServiceInfo info = registeredServiceRepository.findByName(deletionDto.getName());
    if (info == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    registeredServiceRepository.deleteById(deletionDto.getName());
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
