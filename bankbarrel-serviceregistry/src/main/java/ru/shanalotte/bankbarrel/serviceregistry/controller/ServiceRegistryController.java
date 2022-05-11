package ru.shanalotte.bankbarrel.serviceregistry.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
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

  @GetMapping(value = "/services", produces = "application/json")
  public @ResponseBody List<RegisteredServiceInfo> registeredServicesList() {
    List<RegisteredServiceInfo> list = registeredServiceRepository.findAll();
    return list;
  }

  @PostMapping(value = "/services", consumes = "application/json", produces = "application/json")
  public @ResponseBody String createService(@RequestBody RegisteredServiceInfo newService) {
    registeredServiceRepository.save(newService);
    return "SAVED";
  }

  @PutMapping(value = "/services", consumes = "application/json", produces = "application/json")
  public @ResponseBody String updateService(@RequestBody RegisteredServiceInfo newService) {
    registeredServiceRepository.save(newService);
    return "UPDATED";
  }

  @DeleteMapping(value = "/services", consumes = "application/json", produces = "application/json")
  public @ResponseBody String deleteService(@RequestBody ServiceDeletionDto deletionDto) {
    registeredServiceRepository.deleteById(deletionDto.getName());
    return "DELETED";
  }

}
