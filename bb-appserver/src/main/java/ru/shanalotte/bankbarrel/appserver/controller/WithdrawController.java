package ru.shanalotte.bankbarrel.appserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyWithdraw;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountDao;
import ru.shanalotte.bankbarrel.appserver.repository.CurrencyDao;
import ru.shanalotte.bankbarrel.appserver.repository.OperationSourceDao;
import ru.shanalotte.bankbarrel.appserver.repository.WithdrawDao;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.dto.WithdrawDto;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;
import ru.shanalotte.bankbarrel.core.service.SimpleBankService;

/**
 * Контроллер операций снятий денежных средств.
 */
@RestController
@Tag(name = "Withdraws", description = "Снятия средств")
public class WithdrawController {

  private BankAccountDao bankAccountDao;
  private CurrencyDao currencyDao;
  private SimpleBankService bankService;
  private OperationSourceDao operationSourceDao;
  private WithdrawDao withdrawDao;
  private static final Logger logger = LoggerFactory.getLogger(WithdrawController.class);

  /**
   * Конструктор со всеми зависимостями.
   */
  public WithdrawController(BankAccountDao bankAccountDao,
                            CurrencyDao currencyDao,
                            SimpleBankService bankService,
                            OperationSourceDao operationSourceDao,
                            WithdrawDao withdrawDao) {
    this.bankAccountDao = bankAccountDao;
    this.currencyDao = currencyDao;
    this.bankService = bankService;
    this.operationSourceDao = operationSourceDao;
    this.withdrawDao = withdrawDao;
  }

  /**
   * Получение информации о снятии средств по ID.
   */
  @Operation(summary = "Получить информацию о снятии средств по ID")
  @GetMapping("/withdraw/{id}")
  public ResponseEntity<WithdrawDto> withdrawInfo(@Parameter(description = "ID снятия средств") @PathVariable("id") Long id) {
    logger.info("GET /withdraw/{}", id);
    if (!withdrawDao.findById(id).isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    MoneyWithdraw entity = withdrawDao.getById(id);
    WithdrawDto dto = new WithdrawDto();
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
   * Инициация операции снятия средств.
   */
  @Operation(summary = "Создать снятие средств")
  @PostMapping(value = "/withdraw", consumes = "application/json", produces = "application/json")
  public ResponseEntity<WithdrawDto> createWithdraw(@RequestBody WithdrawDto dto)
      throws JsonProcessingException {
    logger.info("POST /withdraw {}", new ObjectMapper().writeValueAsString(dto));
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
    MoneyWithdraw moneyWithdraw = new MoneyWithdraw();
    moneyWithdraw.setCurrency(currencyDao.findByCode(dto.getCurrency()));
    moneyWithdraw.setTimestamp(Timestamp.valueOf(dto.getTimestamp()));
    moneyWithdraw.setAccount(account);
    moneyWithdraw.setAmount(dto.getAmount());
    moneyWithdraw.setOperationSource(operationSourceDao.findByName(dto.getSource()));
    try {
      bankService.withdraw(account, new MonetaryAmount(dto.getAmount(), dto.getCurrency()));
      bankAccountDao.save(account);
      moneyWithdraw.setResult("SUCCESS");
      dto.setResult("SUCCESS");
      withdrawDao.save(moneyWithdraw);
      dto.setId(moneyWithdraw.getId());
      return new ResponseEntity<>(dto, HttpStatus.OK);
    } catch (UnknownCurrencyRate unknownCurrencyRate) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (InsufficientFundsException e) {
      moneyWithdraw.setResult("REJECTED: NOT ENOUGH FUNDS");
      dto.setResult("REJECTED: NOT ENOUGH FUNDS");
      withdrawDao.save(moneyWithdraw);
      dto.setId(moneyWithdraw.getId());
      return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
  }
}
