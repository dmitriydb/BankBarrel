package ru.shanalotte.bankbarrel.appserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.bankbarrel.appserver.repository.BankClientDao;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;

/**
 * Контроллер для информации о клиентах.
 */
@RestController
@Tag(name = "Clients", description = "Клиенты")
public class ClientsController {

  private BankClientDao bankClientDao;
  private static final Logger logger = LoggerFactory.getLogger(ClientsController.class);

  public ClientsController(BankClientDao bankClientDao) {
    this.bankClientDao = bankClientDao;
  }

  /**
   * Создание нового клиента.
   */
  @Operation(summary = "Добавить клиента")
  @PostMapping("/clients")
  public ResponseEntity<BankClientDto> createNewClient(@RequestBody BankClientDto dto)
      throws JsonProcessingException {
    logger.info("POST /clients {}", new ObjectMapper().writeValueAsString(dto));
    BankClient client = new BankClient.Builder(dto.getGivenName(), dto.getFamilyName())
        .withEmail(dto.getEmail())
        .withTelephone(dto.getTelephone())
        .build();
    bankClientDao.save(client);
    dto.setId(client.getId());
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
  }

  /**
   * Получение списка клиентов.
   */
  @Operation(summary = "Получить список клиентов")
  @GetMapping("/clients")
  public ResponseEntity<List<BankClientDto>> getClientList() {
    logger.info("GET /clients");
    List<BankClientDto> result = bankClientDao.findAll()
        .stream()
        .map(e -> {
          BankClientDto dto = new BankClientDto();
          dto.setEmail(e.getEmail());
          dto.setTelephone(e.getTelephone());
          dto.setFamilyName(e.getFamilyName());
          dto.setGivenName(e.getGivenName());
          dto.setId(e.getId());
          return dto;
        }).collect(Collectors.toList());
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  /**
   * Получение информации о клиенте с определенноым ID.
   */
  @Operation(summary = "Получить информацию о клиенте")
  @GetMapping("/clients/{id}")
  public ResponseEntity<BankClientDto> getClientInfo(
      @Parameter(description = "ID клиента")
      @PathVariable("id") Long id) {
    logger.info("GET /clients/{}", id);
    if (id == null || !bankClientDao.findById(id).isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    BankClient client = bankClientDao.getById(id);
    BankClientDto dto = new BankClientDto();
    dto.setId(client.getId());
    dto.setEmail(client.getEmail());
    dto.setFamilyName(client.getFamilyName());
    dto.setTelephone(client.getTelephone());
    dto.setGivenName(client.getGivenName());
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  /**
   * Изменение информации клиента с определенным ID.
   */
  @Operation(summary = "Обновить информацию о клиенте")
  @PutMapping("/clients/{id}")
  @Transactional
  public ResponseEntity<BankClientDto> updateClientInfo(
      @Parameter(description = "ID клиента")
      @PathVariable("id") Long id,
      @RequestBody BankClientDto dto) {
    logger.info("PUT /clients/{}", id);
    if (!bankClientDao.findById(id).isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    BankClient client = bankClientDao.getById(id);
    client.setEmail(dto.getEmail());
    client.setGivenName(dto.getGivenName());
    client.setFamilyName(dto.getFamilyName());
    client.setTelephone(dto.getTelephone());
    bankClientDao.save(client);
    dto.setId(client.getId());
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }
}
