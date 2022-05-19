package ru.shanalotte.bankbarrel.appserver.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyDeposit;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountDao;
import ru.shanalotte.bankbarrel.appserver.repository.CurrencyDao;
import ru.shanalotte.bankbarrel.appserver.repository.DepositDao;
import ru.shanalotte.bankbarrel.appserver.repository.OperationSourceDao;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.dto.DepositDto;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;
import ru.shanalotte.bankbarrel.core.service.SimpleBankService;

/**
 * Контроллер для операций денежных вкладов.
 */
@RestController
public class DepositController {

  private BankAccountDao bankAccountDao;
  private CurrencyDao currencyDao;
  private SimpleBankService bankService;
  private OperationSourceDao operationSourceDao;
  private DepositDao depositDao;

  /**
   * Конструктор со всеми зависимостями.
   */
  public DepositController(BankAccountDao bankAccountDao,
                           CurrencyDao currencyDao,
                           SimpleBankService bankService,
                           OperationSourceDao operationSourceDao,
                           DepositDao depositDao) {
    this.bankAccountDao = bankAccountDao;
    this.currencyDao = currencyDao;
    this.bankService = bankService;
    this.operationSourceDao = operationSourceDao;
    this.depositDao = depositDao;
  }

  /**
   * Получить информацию о вкладе с определенным ID.
   */
  @GetMapping("/deposit/{id}")
  public ResponseEntity<DepositDto> depositInfo(@PathVariable("id") Long id) {
    if (!depositDao.findById(id).isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    MoneyDeposit entity = depositDao.getById(id);
    DepositDto dto = new DepositDto();
    dto.setResult(entity.getResult());
    dto.setId(entity.getId());
    dto.setSource(entity.getOperationSource().getName());
    dto.setTimestamp(entity.getTimestamp().toString());
    dto.setAccount(entity.getAccount().getIdentifier());
    dto.setAmount(entity.getAmount());
    dto.setCurrency(entity.getCurrency().getCode());
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  /**
   * Создание вклада.
   */
  @PostMapping(value = "/deposit", consumes = "application/json", produces = "application/json")
  public ResponseEntity<DepositDto> createDeposit(@RequestBody DepositDto dto) {
    if (!bankAccountDao.findById(dto.getAccount()).isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    if (currencyDao.findByCode(dto.getCurrency()) == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (dto.getTimestamp() == null) {
      dto.setTimestamp(String.valueOf(Timestamp.valueOf(LocalDateTime.now())));
    }
    dto.setSource("bb-webapp");
    BankAccount account = bankAccountDao.getById(dto.getAccount());
    try {
      bankService.deposit(account, new MonetaryAmount(dto.getAmount(), dto.getCurrency()));
      bankAccountDao.save(account);
      MoneyDeposit moneyDeposit = new MoneyDeposit();
      moneyDeposit.setCurrency(currencyDao.findByCode(dto.getCurrency()));
      moneyDeposit.setTimestamp(Timestamp.valueOf(dto.getTimestamp()));
      moneyDeposit.setAccount(account);
      moneyDeposit.setAmount(dto.getAmount());
      moneyDeposit.setOperationSource(operationSourceDao.findByName(dto.getSource()));
      moneyDeposit.setResult("SUCCESS");
      dto.setResult("SUCCESS");
      depositDao.save(moneyDeposit);
      dto.setId(moneyDeposit.getId());
      return new ResponseEntity<>(dto, HttpStatus.OK);
    } catch (UnknownCurrencyRate e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
