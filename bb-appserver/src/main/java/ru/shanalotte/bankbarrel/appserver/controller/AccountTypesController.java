package ru.shanalotte.bankbarrel.appserver.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.bankbarrel.appserver.domain.BankAccountAdditionalTypeEntity;
import ru.shanalotte.bankbarrel.appserver.domain.BankAccountTypeEntity;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountAdditionalTypeDao;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountTypeDao;
import ru.shanalotte.bankbarrel.core.dto.AccountAdditionalTypeDto;
import ru.shanalotte.bankbarrel.core.dto.AccountTypeDto;

/**
 * Контроллер для запросов к типам банковских счетов 1 уровня.
 */
@RestController
public class AccountTypesController {

  private static final Logger logger = LoggerFactory.getLogger(AccountTypesController.class);
  private BankAccountTypeDao bankAccountTypeDao;
  private BankAccountAdditionalTypeDao bankAccountAdditionalTypeDao;

  public AccountTypesController(BankAccountTypeDao bankAccountTypeDao,
                                BankAccountAdditionalTypeDao bankAccountAdditionalTypeDao) {
    this.bankAccountTypeDao = bankAccountTypeDao;
    this.bankAccountAdditionalTypeDao = bankAccountAdditionalTypeDao;
  }

  /**
   * Получение информации о всех типах счетов 1 уровня.
   */
  @GetMapping("/accounttypes")
  public ResponseEntity<List<AccountTypeDto>> getAccountTypes() {
    logger.info("GET /accounttypes");
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

  /**
   * Получение информации о типе банковского счета 1 уровня по коду.
   */
  @GetMapping("/accounttypes/{code}")
  public ResponseEntity<AccountTypeDto> getAccountTypeByCode(@PathVariable("code") String code) {
    logger.info("GET /accounttypes/{}", code);
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

  /**
   * Получение информации о подтипах типа банковского счета 1 уровня по коду.
   */
  @GetMapping("/accounttypes/{code}/additionaltypes")
  public ResponseEntity<List<AccountAdditionalTypeDto>> getAdditionalTypes(
      @PathVariable("code") String code) {
    logger.info("GET /accounttypes/{}/additionaltypes", code);
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
    dtos.sort(Comparator.comparing(AccountAdditionalTypeDto::getId));
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  /**
   * Получение информации о типе банковского счета 2 уровня по коду.
   */
  @GetMapping("/additionalaccounttypes/{code}")
  public ResponseEntity<AccountAdditionalTypeDto> getAdditionalTypeInfo(
      @PathVariable("code") String code
  ) {
    logger.info("GET /additionalaccounttypes/{}", code);
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

  /**
   * Добавление подтипа типу банковского счета 1 уровня.
   */
  @PostMapping(value = "/accounttypes/{code}/additionaltypes",
      consumes = "application/json", produces = "application/json")
  public ResponseEntity<AccountAdditionalTypeDto> createAdditionalType(
      @PathVariable("code") String code,
                       @RequestBody AccountAdditionalTypeDto dto) throws JsonProcessingException {
    logger.info("POST /accounttypes/{}/additionaltypes {}", code,
        new ObjectMapper().writeValueAsString(dto));
    BankAccountTypeEntity entity = bankAccountTypeDao.findByCode(code);
    if (entity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    BankAccountAdditionalTypeEntity existingNewEntity =
        bankAccountAdditionalTypeDao.findByCode(dto.getType());
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

  /**
   * Удаление подтипа из типа банковского счета 1 уровня.
   */
  @DeleteMapping(value = "/accounttypes/{code}/additionaltypes",
      consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> deleteAdditionalType(@PathVariable("code") String code,
                                                @RequestBody AccountAdditionalTypeDto dto)
      throws JsonProcessingException {
    logger.info("DELETE /accounttypes/{}/additionaltypes, {}", code,
        new ObjectMapper().writeValueAsString(dto));
    BankAccountTypeEntity entity = bankAccountTypeDao.findByCode(code);
    if (entity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    BankAccountAdditionalTypeEntity existingEntity =
        bankAccountAdditionalTypeDao.findByCode(dto.getType());
    if (existingEntity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    entity.getSubTypes().remove(existingEntity);
    bankAccountTypeDao.save(entity);
    bankAccountAdditionalTypeDao.delete(existingEntity);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Создание банковского счета 1 уровня.
   */
  @PostMapping(value = "/accounttypes",
      consumes = "application/json", produces = "application/json")
  public ResponseEntity<AccountTypeDto> createNewAccountType(@RequestBody AccountTypeDto dto)
      throws JsonProcessingException {
    logger.info("POST /accounttypes, {}",
        new ObjectMapper().writeValueAsString(dto));
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

  /**
   * Удаление банковского счета 1 уровня.
   */
  @DeleteMapping(value = "/accounttypes",
      consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> deleteAccountType(@RequestBody AccountTypeDto dto)
      throws JsonProcessingException {
    logger.info("DELETE /accounttypes, {}",
        new ObjectMapper().writeValueAsString(dto));
    BankAccountTypeEntity existingEntity = bankAccountTypeDao.findByCode(dto.getType());
    if (existingEntity == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    bankAccountTypeDao.delete(existingEntity);
    return new ResponseEntity<>(HttpStatus.OK);
  }


}
