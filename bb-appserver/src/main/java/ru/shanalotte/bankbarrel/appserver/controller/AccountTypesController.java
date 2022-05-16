package ru.shanalotte.bankbarrel.appserver.controller;

import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shanalotte.bankbarrel.appserver.domain.BankAccountAdditionalTypeEntity;
import ru.shanalotte.bankbarrel.appserver.domain.BankAccountTypeEntity;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountAdditionalTypeDao;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountTypeDao;
import ru.shanalotte.bankbarrel.core.domain.BankAccountType;
import ru.shanalotte.bankbarrel.core.dto.AccountAdditionalTypeDto;
import ru.shanalotte.bankbarrel.core.dto.AccountTypeDto;

@RestController
public class AccountTypesController {

  private BankAccountTypeDao bankAccountTypeDao;
  private BankAccountAdditionalTypeDao bankAccountAdditionalTypeDao;

  public AccountTypesController(BankAccountTypeDao bankAccountTypeDao, BankAccountAdditionalTypeDao bankAccountAdditionalTypeDao) {
    this.bankAccountTypeDao = bankAccountTypeDao;
    this.bankAccountAdditionalTypeDao = bankAccountAdditionalTypeDao;
  }

  @GetMapping("/accounttypes")
  public ResponseEntity<List<AccountTypeDto>> getAccountTypes() {
    List<BankAccountTypeEntity> accountTypes = bankAccountTypeDao.findAll();
    List<AccountTypeDto> result = new ArrayList<>();
    for (BankAccountTypeEntity entity : accountTypes) {
      AccountTypeDto dto = new AccountTypeDto();
      dto.setType(entity.getCode());
      dto.setId(entity.getId());
      dto.setDescription(entity.getDescription());
      result.add(dto);
    }
    Collections.sort(result, Comparator.comparing(AccountTypeDto::getId));

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping("/accounttypes/{code}")
  public ResponseEntity<AccountTypeDto> getAccountTypeByCode(@PathVariable("code") String code) {
    BankAccountTypeEntity entity = bankAccountTypeDao.findByCode(code);
    if (entity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    AccountTypeDto dto = new AccountTypeDto();
    dto.setType(entity.getCode());
    dto.setId(entity.getId());
    dto.setDescription(entity.getDescription());
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @GetMapping("/accounttypes/{code}/additionaltypes")
  public ResponseEntity<List<AccountAdditionalTypeDto>> getAdditionalTypes(@PathVariable("code") String code) {
    BankAccountTypeEntity existingEntity = bankAccountTypeDao.findByCode(code);
    if (existingEntity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    Set<BankAccountAdditionalTypeEntity> entities = existingEntity.getSubTypes();
    List<AccountAdditionalTypeDto> dtos = new ArrayList<>();
    for (BankAccountAdditionalTypeEntity entity : entities) {
      AccountAdditionalTypeDto dto = new AccountAdditionalTypeDto();
      dto.setDescription(entity.getDescription());
      dto.setId(entity.getId());
      dto.setType(entity.getCode());
      dtos.add(dto);
    }
    Collections.sort(dtos, Comparator.comparing(AccountAdditionalTypeDto::getId));
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  @GetMapping("/additionalaccounttypes/{code}")
  public ResponseEntity<AccountAdditionalTypeDto> getAdditionalTypeInfo(@PathVariable("code") String code
  ) {
    BankAccountAdditionalTypeEntity existingEntity = bankAccountAdditionalTypeDao.findByCode(code);
    System.out.println(existingEntity);
    if (existingEntity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    AccountAdditionalTypeDto dto = new AccountAdditionalTypeDto();
    dto.setDescription(existingEntity.getDescription());
    dto.setId(existingEntity.getId());
    dto.setType(existingEntity.getCode());
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @PostMapping(value = "/accounttypes/{code}/additionaltypes", consumes = "application/json", produces = "application/json")
  public ResponseEntity<AccountAdditionalTypeDto> createAdditionalType(@PathVariable("code") String code,
                                                                       @RequestBody AccountAdditionalTypeDto dto) {
    BankAccountTypeEntity entity = bankAccountTypeDao.findByCode(code);
    if (entity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    BankAccountAdditionalTypeEntity existingNewEntity = bankAccountAdditionalTypeDao.findByCode(dto.getType());
    if (existingNewEntity != null) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    BankAccountAdditionalTypeEntity newEntity = new BankAccountAdditionalTypeEntity();
    newEntity.setCode(dto.getType());
    newEntity.setDescription(dto.getDescription());
    newEntity.setOwnerType(entity);
    bankAccountAdditionalTypeDao.save(newEntity);
    entity.getSubTypes().add(newEntity);
    bankAccountTypeDao.save(entity);
    AccountAdditionalTypeDto result = new AccountAdditionalTypeDto();
    result.setId(newEntity.getId());
    result.setType(newEntity.getCode());
    result.setDescription(newEntity.getDescription());
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/accounttypes/{code}/additionaltypes", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> deleteAdditionalType(@PathVariable("code") String code,
                                                                       @RequestBody AccountAdditionalTypeDto dto) {
    BankAccountTypeEntity entity = bankAccountTypeDao.findByCode(code);
    if (entity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    BankAccountAdditionalTypeEntity existingEntity = bankAccountAdditionalTypeDao.findByCode(dto.getType());
    if (existingEntity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    entity.getSubTypes().remove(existingEntity);
    bankAccountTypeDao.save(entity);
    bankAccountAdditionalTypeDao.delete(existingEntity);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping(value = "/accounttypes", consumes = "application/json", produces = "application/json")
  public ResponseEntity<AccountTypeDto> createNewAccountType(@RequestBody AccountTypeDto dto) {
    BankAccountTypeEntity existingEntity = bankAccountTypeDao.findByCode(dto.getType());
    if (existingEntity != null) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    BankAccountTypeEntity entity = new BankAccountTypeEntity();
    entity.setCode(dto.getType());
    entity.setDescription(dto.getDescription());
    bankAccountTypeDao.save(entity);
    AccountTypeDto result = new AccountTypeDto();
    result.setDescription(entity.getDescription());
    result.setId(entity.getId());
    result.setType(entity.getCode());
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/accounttypes", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> deleteAccountType(@RequestBody AccountTypeDto dto) {
    BankAccountTypeEntity existingEntity = bankAccountTypeDao.findByCode(dto.getType());
    if (existingEntity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    bankAccountTypeDao.delete(existingEntity);
    return new ResponseEntity<>(HttpStatus.OK);
  }


}
