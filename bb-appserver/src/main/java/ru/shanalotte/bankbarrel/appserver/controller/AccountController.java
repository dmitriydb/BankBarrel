package ru.shanalotte.bankbarrel.appserver.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shanalotte.bankbarrel.appserver.repository.*;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.BankAccountType;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;

@RestController
public class AccountController {

  private BankAccountTypeDao bankAccountTypeDao;
  private BankAccountAdditionalTypeDao bankAccountAdditionalTypeDao;
  private BankClientDao bankClientDao;
  private BankAccountDao bankAccountDao;
  private CurrencyDao currencyDao;

  public AccountController(BankAccountTypeDao bankAccountTypeDao, BankAccountAdditionalTypeDao bankAccountAdditionalTypeDao, BankClientDao bankClientDao, BankAccountDao bankAccountDao, CurrencyDao currencyDao) {
    this.bankAccountTypeDao = bankAccountTypeDao;
    this.bankAccountAdditionalTypeDao = bankAccountAdditionalTypeDao;
    this.bankClientDao = bankClientDao;
    this.bankAccountDao = bankAccountDao;
    this.currencyDao = currencyDao;
  }

  @GetMapping("/accounts/{number}")
  public ResponseEntity<BankAccountDto> getAccountInfo(@PathVariable("number") String number) {
    BankAccount account = bankAccountDao.findByNumber(number);
    if (account == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    BankAccountDto dto = new BankAccountDto();
    dto.setIdentifier(account.getIdentifier());
    dto.setNumber(account.getNumber());
    dto.setType(account.getBankAccountType());
    dto.setAdditionalType(account.getAdditionalType());
    dto.setOwner(account.getOwner().getId());
    dto.setDescription(account.getDescription());
    dto.setCurrency(account.getCurrency());
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @DeleteMapping("/accounts")
  public ResponseEntity<?> deleteAccount(@RequestBody BankAccountDto dto) {
    BankAccount account = bankAccountDao.findByNumber(dto.getNumber());
    if (account == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    BankClient client = bankClientDao.getById(account.getOwner().getId());
    client.getAccounts().remove(account);
    bankClientDao.save(client);
    bankAccountDao.delete(account);
    return new ResponseEntity<>(HttpStatus.OK);
  }

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
      dto.setDescription(account.getDescription());
      dto.setOwner(account.getOwner().getId());
      dto.setType(account.getBankAccountType());
      dto.setNumber(account.getNumber());
      dto.setAdditionalType(account.getAdditionalType());
      result.add(dto);
    }
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

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
