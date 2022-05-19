package ru.shanalotte.bankbarrel.serviceregistry.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.bankbarrel.serviceregistry.dto.RegisteredServiceInfo;
import ru.shanalotte.bankbarrel.serviceregistry.dto.ServiceDeletionDto;
import ru.shanalotte.bankbarrel.serviceregistry.repository.RegisteredServiceRepository;

/**
 * Контроллер, который обрабатывает запросы на добавление и удаление информации
 * о зарегистрированных микросервисах.
 */
@RestController
public class ServiceRegistryController {

  private RegisteredServiceRepository registeredServiceRepository;

  private static final Logger logger = LoggerFactory.getLogger(ServiceRegistryController.class);

  public ServiceRegistryController(RegisteredServiceRepository registeredServiceRepository) {
    this.registeredServiceRepository = registeredServiceRepository;
  }

  /**
   * Получение списка микросервисов.
   */
  @Operation(description = "Get registered microservices list")
  @GetMapping(value = "/services", produces = "application/json")
  public @ResponseBody
  List<RegisteredServiceInfo> registeredServicesList() {
    logger.info("GET /services");
    List<RegisteredServiceInfo> list = registeredServiceRepository.findAll();
    return list;
  }

  /**
  Получение информации о микросервисе по имени.
   */
  @ApiResponse(responseCode = "404",
      description = "Service with such name is not registered", content = @Content)
  @Operation(description = "Get microservice info by name")
  @GetMapping(value = "/services/{name}", produces = "application/json")
  public ResponseEntity<RegisteredServiceInfo> getServiceInfo(@Parameter(
      description = "Microservice name") @PathVariable("name") String serviceName) {
    logger.info("GET /services/{}", serviceName);
    RegisteredServiceInfo info = registeredServiceRepository.findByName(serviceName);
    if (info == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(info, HttpStatus.OK);
  }

  /**
   * Создание информации о микросервисе.
   */
  @Operation(description = "Create microservice information")
  @PostMapping(value = "/services", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> createService(@Schema(implementation = RegisteredServiceInfo.class)
                                         @RequestBody RegisteredServiceInfo newService)
      throws JsonProcessingException {
    logger.info("POST /services/ data = {}", new ObjectMapper().writeValueAsString(newService));
    registeredServiceRepository.save(newService);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * Обновление информации о микросервисе.
  */
  @Operation(description = "Update existing microservice info or create new one")
  @PutMapping(value = "/services", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> updateService(@RequestBody RegisteredServiceInfo newService)
      throws JsonProcessingException {
    logger.info("PUT /services/ data = {}", new ObjectMapper().writeValueAsString(newService));
    registeredServiceRepository.save(newService);
    return new ResponseEntity<String>(HttpStatus.OK);
  }

  /**
   * Удалить информацию о микросервисе.
   */
  @Operation(description = "Delete microservice info")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "404",
          description = "Service with such name is not registered", content = @Content),
      @ApiResponse(responseCode = "200",
          description = "Service with such name was found AND successfully deleted",
          content = @Content)
  })
  @DeleteMapping(value = "/services", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> deleteService(@RequestBody ServiceDeletionDto deletionDto)
      throws JsonProcessingException {
    logger.info("DELETE /services/ data = {}", new ObjectMapper().writeValueAsString(deletionDto));
    RegisteredServiceInfo info = registeredServiceRepository.findByName(deletionDto.getName());
    if (info == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    registeredServiceRepository.deleteById(deletionDto.getName());
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
