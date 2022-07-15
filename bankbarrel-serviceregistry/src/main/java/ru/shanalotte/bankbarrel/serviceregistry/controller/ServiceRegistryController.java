package ru.shanalotte.bankbarrel.serviceregistry.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

  private static final Logger logger = LoggerFactory.getLogger(ServiceRegistryController.class);
  private RegisteredServiceRepository registeredServiceRepository;

  public ServiceRegistryController(RegisteredServiceRepository registeredServiceRepository) {
    this.registeredServiceRepository = registeredServiceRepository;
  }

  /**
   * Получение списка микросервисов.
   */
  @Operation(description = "Получить список зарегистрированных микросервисов", summary = "Получить список зарегистрированных микросервисов")
  @GetMapping(value = "/services", produces = "application/json")
  public @ResponseBody
  List<RegisteredServiceInfo> registeredServicesList() {
    logger.info("GET /services");
    return registeredServiceRepository.findAll();
  }

  /**
   * Получение информации о микросервисе по имени.
   */
  @Operation(description = "Получить информацию о состоянии микросервиса по имени сервиса", summary = "Получить информацию о сервисе по имени")
  @ApiResponse(responseCode = "404",
      description = "Сервис с таким именем не найден", content = @Content)
  @GetMapping(value = "/services/{name}", produces = "application/json")
  public ResponseEntity<RegisteredServiceInfo> getServiceInfo(@Parameter(
      description = "Microservice name", examples = @ExampleObject(
      name = "bb-webapp", value = "bb-webapp", summary = "bb-webapp", description = "Получить информацию о сервисе с именем bb-webapp"
  )) @PathVariable("name") String serviceName) {
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
  @Operation(description = "Регистрация сервиса в реестре", summary = "Регистрация сервера в реестре")
  @PostMapping(value = "/services", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> createService(@RequestBody RegisteredServiceInfo newService)
      throws JsonProcessingException {
    logger.info("POST /services/ data = {}", new ObjectMapper().writeValueAsString(newService));
    registeredServiceRepository.save(newService);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * Обновление информации о микросервисе.
   */
  @Operation(description = "Обновление информации о сервисе или создание нового", summary = "Обновление информации о сервисе")
  @ApiResponse(responseCode = "200", description = "Обновление информации или создание нового сервиса произошло успешно")
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
  @Operation(description = "Удаление информации о сервисе", summary = "Удаление информации о сервисе")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "404",
          description = "Сервис с таким именем не найден", content = @Content),
      @ApiResponse(responseCode = "200",
          description = "Сервис успешно удален",
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
