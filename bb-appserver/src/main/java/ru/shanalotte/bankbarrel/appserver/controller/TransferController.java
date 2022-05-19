package ru.shanalotte.bankbarrel.appserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyTransfer;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountDao;
import ru.shanalotte.bankbarrel.appserver.repository.CurrencyDao;
import ru.shanalotte.bankbarrel.appserver.repository.OperationSourceDao;
import ru.shanalotte.bankbarrel.appserver.repository.TransferDao;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.dto.TransferDto;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;
import ru.shanalotte.bankbarrel.core.service.SimpleBankService;

/**
 * Контроллер операций денежных переводов.
 */
@RestController
public class TransferController {

  private BankAccountDao bankAccountDao;
  private CurrencyDao currencyDao;
  private SimpleBankService bankService;
  private OperationSourceDao operationSourceDao;
  private TransferDao transferDao;
  private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

  /**
   * Конструктор со всеми зависимостями.
   */

  public TransferController(BankAccountDao bankAccountDao, CurrencyDao currencyDao,
                            SimpleBankService bankService,
                            OperationSourceDao operationSourceDao, TransferDao transferDao) {
    this.bankAccountDao = bankAccountDao;
    this.currencyDao = currencyDao;
    this.bankService = bankService;
    this.operationSourceDao = operationSourceDao;
    this.transferDao = transferDao;
  }

  /**
   * Получить информацию о денежном переводе по его ID.
   */
  @GetMapping("/transfer/{id}")
  public ResponseEntity<TransferDto> transferInfo(@PathVariable("id") Long id) {
    logger.info("GET /transfer/{}", id);
    if (!transferDao.findById(id).isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    MoneyTransfer entity = transferDao.getById(id);
    TransferDto dto = new TransferDto();
    dto.setResult(entity.getResult());
    dto.setId(entity.getId());
    dto.setSource(entity.getOperationSource().getName());
    dto.setTimestamp(entity.getTimestamp().toString());
    dto.setFromAccount(entity.getFromAccount().getIdentifier());
    dto.setToAccount(entity.getToAccount().getIdentifier());
    dto.setAmount(entity.getAmount());
    dto.setCurrency(entity.getCurrency().getCode());
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  /**
   * Инициировать процесс денежного перевода.
   */
  @PostMapping(value = "/transfer", consumes = "application/json", produces = "application/json")
  public ResponseEntity<TransferDto> createTransfer(@RequestBody TransferDto dto)
      throws JsonProcessingException {
    logger.info("POST /transfer {}", new ObjectMapper().writeValueAsString(dto));
    System.out.println(dto);
    if (!bankAccountDao.findById(dto.getFromAccount()).isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    if (!bankAccountDao.findById(dto.getToAccount()).isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    if (currencyDao.findByCode(dto.getCurrency()) == null) {
      System.out.println("Null currency");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (dto.getTimestamp() == null) {
      dto.setTimestamp(String.valueOf(Timestamp.valueOf(LocalDateTime.now())));
    }
    dto.setSource("bb-webapp");
    BankAccount fromAccount = bankAccountDao.getById(dto.getFromAccount());
    BankAccount toAccount = bankAccountDao.getById(dto.getToAccount());
    MoneyTransfer moneyTransfer = new MoneyTransfer();
    moneyTransfer.setCurrency(currencyDao.findByCode(dto.getCurrency()));
    moneyTransfer.setTimestamp(Timestamp.valueOf(dto.getTimestamp()));
    moneyTransfer.setFromAccount(fromAccount);
    moneyTransfer.setToAccount(toAccount);
    moneyTransfer.setAmount(dto.getAmount());
    moneyTransfer.setOperationSource(operationSourceDao.findByName(dto.getSource()));
    try {
      bankService.transfer(fromAccount, toAccount,
          new MonetaryAmount(dto.getAmount(), dto.getCurrency()));
      bankAccountDao.save(fromAccount);
      bankAccountDao.save(toAccount);
      moneyTransfer.setResult("SUCCESS");
      dto.setResult("SUCCESS");
      transferDao.save(moneyTransfer);
      dto.setId(moneyTransfer.getId());
      return new ResponseEntity<>(dto, HttpStatus.OK);
    } catch (UnknownCurrencyRate unknownCurrencyRate) {
      System.out.println("Unknown currency");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (InsufficientFundsException e) {
      System.out.println("Not enough money");
      moneyTransfer.setResult("REJECTED: NOT ENOUGH FUNDS");
      transferDao.save(moneyTransfer);
      dto.setResult("REJECTED: NOT ENOUGH FUNDS");
      dto.setId(moneyTransfer.getId());
      return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
  }
}
