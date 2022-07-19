package ru.shanalotte.bankbarrel.appserver.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountAdditionalTypeDao;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountDao;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountTypeDao;
import ru.shanalotte.bankbarrel.appserver.repository.BankClientDao;
import ru.shanalotte.bankbarrel.appserver.repository.CurrencyDao;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;

/**
 * Контроллер для информации о банковских счетах.
 */
@RestController
@Tag(name = "BankAccounts", description = "Банковские счета")
 public class AccountController {

  private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
  private BankAccountTypeDao bankAccountTypeDao;
  private BankAccountAdditionalTypeDao bankAccountAdditionalTypeDao;
  private BankClientDao bankClientDao;
  private BankAccountDao bankAccountDao;
  private CurrencyDao currencyDao;

  /**
   * Конструктор со всеми зависимостями.
   */
  public AccountController(BankAccountTypeDao bankAccountTypeDao,
                           BankAccountAdditionalTypeDao bankAccountAdditionalTypeDao,
                           BankClientDao bankClientDao,
                           BankAccountDao bankAccountDao,
                           CurrencyDao currencyDao) {
    this.bankAccountTypeDao = bankAccountTypeDao;
    this.bankAccountAdditionalTypeDao = bankAccountAdditionalTypeDao;
    this.bankClientDao = bankClientDao;
    this.bankAccountDao = bankAccountDao;
    this.currencyDao = currencyDao;
  }

  /**
   * Получение информации о банковском счете с определенным номером.
   */
  @Operation(summary = "Получить информацию о банковском счете по номеру")
  @GetMapping("/accounts/{number}")
  @Transactional
  public ResponseEntity<BankAccountDto> getAccountInfo(
      @Parameter(description = "Номер счета", example = "40700000500000000001", required = true
      )
      @PathVariable("number") String number) {
    logger.info("GET /accounts/{}", number);
    BankAccount account = bankAccountDao.findByNumber(number);
    if (account == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    BankAccountDto dto = new BankAccountDto();
    dto.setIdentifier(account.getIdentifier());
    dto.setNumber(account.getNumber());
    dto.setBalance(account.getValue().toString());
    dto.setType(account.getBankAccountType());
    dto.setAdditionalType(account.getAdditionalType());
    dto.setOwner(account.getOwner().getId());
    dto.setDescription(account.getDescription());
    dto.setCurrency(account.getCurrency());
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  /**
   * Получение списка всех банковских счетов.
   */
  @GetMapping("/accounts")
  @Operation(summary = "Получить информацию о всех банковских счетах")
  public ResponseEntity<List<BankAccountDto>> getAllAccounts() {
    logger.info("GET /accounts");
    List<BankAccountDto> result = bankAccountDao.findAll()
        .stream().map(account -> {
          BankAccountDto dto = new BankAccountDto();
          dto.setIdentifier(account.getIdentifier());
          dto.setNumber(account.getNumber());
          dto.setBalance(account.getValue().toString());
          dto.setType(account.getBankAccountType());
          dto.setAdditionalType(account.getAdditionalType());
          dto.setOwner(account.getOwner().getId());
          dto.setDescription(account.getDescription());
          dto.setCurrency(account.getCurrency());
          return dto;
        }).collect(Collectors.toList());
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  /**
   * Удаление банковского счета по идентификатору (UUID).
   */
  @Operation(summary = "Удалить банковский счет по ID")
  @ApiResponse(responseCode = "404", description = "Счет с таким ID не существует")
  @ApiResponse(responseCode = "200", description = "Счет успешно удален")
  @DeleteMapping("/accounts/{id}")
  @Transactional
  public ResponseEntity<?> deleteAccount(@Parameter(description = "ID удаляемого счетa") @PathVariable("id") String id) {
    logger.info("DELETE /accounts/{}", id);
    Optional<BankAccount> account = bankAccountDao.findById(id);
    if (!account.isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    BankClient client = bankClientDao.getById(account.get().getOwner().getId());
    client.getAccounts().remove(account.get());
    bankClientDao.save(client);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Получение списка счетов клиента банка с определенным ID.
   */
  @Operation(summary = "Получить список счетов клиента по его ID")
  @ApiResponse(responseCode = "404", description = "Клиент с указанным ID не найден")
  @GetMapping("/clients/{id}/accounts")
  @Transactional
  public ResponseEntity<List<BankAccountDto>> getClientAccounts(@PathVariable("id") @Parameter(description = "ID клиента") Long id) {
    logger.info("GET /clients/{}/accounts", id);
    if (!bankClientDao.findById(id).isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    List<BankAccountDto> result = new ArrayList<>();
    BankClient client = bankClientDao.getById(id);
    for (BankAccount account : client.getAccounts()) {
      BankAccountDto dto = new BankAccountDto();
      dto.setIdentifier(account.getIdentifier());
      dto.setCurrency(account.getCurrency());
      dto.setBalance(account.getValue().toString());
      dto.setDescription(account.getDescription());
      dto.setOwner(account.getOwner().getId());
      dto.setType(account.getBankAccountType());
      dto.setNumber(account.getNumber());
      dto.setAdditionalType(account.getAdditionalType());
      result.add(dto);
    }
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  /**
   * Запрос на открытие нового банковского счета.
   */
  @Operation(summary = "Открыть новый счет")
  @PostMapping("/accounts")
  @Transactional
  @ApiResponse(responseCode = "400", description = "В случае, если данные не прошли валидацию. Возможные причины: "
      + "1) неверный ID клиента 2) неверный тип счета 3) неверный код валюты")
  @ApiResponse(responseCode = "401", description = "Счет с таким номером уже существует")
  public ResponseEntity<BankAccountDto> createAccount(@RequestBody BankAccountDto dto)
      throws JsonProcessingException {
    logger.info("POST /accounts {}", new ObjectMapper().writeValueAsString(dto));
    if (dto.getOwner() == null || !bankClientDao.findById(dto.getOwner()).isPresent()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (bankAccountDao.findByNumber(dto.getNumber()) != null) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    if (bankAccountTypeDao.findByCode(dto.getType()) == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (bankAccountAdditionalTypeDao.findByCode(dto.getAdditionalType()) == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (currencyDao.findByCode(dto.getCurrency()) == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    BankAccount account = new BankAccount();
    account.setBankAccountType(dto.getType());
    account.setAdditionalType(dto.getAdditionalType());
    account.setIdentifier(UUID.randomUUID().toString());
    dto.setIdentifier(account.getIdentifier());
    account.setNumber(dto.getNumber());
    account.setDescription(dto.getDescription());
    account.setCurrency(dto.getCurrency());
    account.setOwner(bankClientDao.getById(dto.getOwner()));
    bankAccountDao.save(account);
    BankClient client = bankClientDao.getById(dto.getOwner());
    client.getAccounts().add(account);
    bankClientDao.save(client);
    System.out.println("B = " + dto.getIdentifier());
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
  }
}
