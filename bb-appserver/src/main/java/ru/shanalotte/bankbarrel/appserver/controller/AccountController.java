package ru.shanalotte.bankbarrel.appserver.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class AccountController {

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
  @GetMapping("/accounts/{number}")
  public ResponseEntity<BankAccountDto> getAccountInfo(@PathVariable("number") String number) {
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
  public ResponseEntity<List<BankAccountDto>> getAllAccounts() {
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
  @DeleteMapping("/accounts/{id}")
  public ResponseEntity<?> deleteAccount(@PathVariable("id") String id) {
    Optional<BankAccount> account = bankAccountDao.findById(id);
    if (!account.isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    BankClient client = bankClientDao.getById(account.get().getOwner().getId());
    client.getAccounts().remove(account.get());
    bankClientDao.save(client);
    bankAccountDao.delete(account.get());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Получение списка счетов клиента банка с определенным ID.
   */
  @GetMapping("/clients/{id}/accounts")
  public ResponseEntity<List<BankAccountDto>> getClientAccounts(@PathVariable("id") Long id) {
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
  @PostMapping("/accounts")
  public ResponseEntity<BankAccountDto> createAccount(@RequestBody BankAccountDto dto) {
    if (!bankClientDao.findById(dto.getOwner()).isPresent()) {
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
    account.setNumber(dto.getNumber());
    account.setDescription(dto.getDescription());
    account.setCurrency(dto.getCurrency());
    account.setOwner(bankClientDao.getById(dto.getOwner()));
    bankAccountDao.save(account);
    BankClient client = bankClientDao.getById(dto.getOwner());
    client.getAccounts().add(account);
    bankClientDao.save(client);
    dto.setIdentifier(account.getIdentifier());
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
  }
}
