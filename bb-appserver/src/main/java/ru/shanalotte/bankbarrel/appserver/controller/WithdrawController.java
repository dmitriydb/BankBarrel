package ru.shanalotte.bankbarrel.appserver.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyDeposit;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyWithdraw;
import ru.shanalotte.bankbarrel.appserver.repository.*;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.dto.DepositDto;
import ru.shanalotte.bankbarrel.core.dto.WithdrawDto;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;
import ru.shanalotte.bankbarrel.core.service.BankService;

@RestController
public class WithdrawController {

  private BankAccountDao bankAccountDao;
  private CurrencyDao currencyDao;
  private BankService bankService;
  private OperationSourceDao operationSourceDao;
  private WithdrawDao withdrawDao;

  public WithdrawController(BankAccountDao bankAccountDao, CurrencyDao currencyDao, BankService bankService, OperationSourceDao operationSourceDao, WithdrawDao withdrawDao) {
    this.bankAccountDao = bankAccountDao;
    this.currencyDao = currencyDao;
    this.bankService = bankService;
    this.operationSourceDao = operationSourceDao;
    this.withdrawDao = withdrawDao;
  }

  @GetMapping("/withdraw/{id}")
  public ResponseEntity<WithdrawDto> withdrawInfo(@PathVariable("id") Long id) {
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

  @PostMapping(value = "/withdraw", consumes = "application/json", produces = "application/json")
  public ResponseEntity<WithdrawDto> createWithdraw(@RequestBody WithdrawDto dto) {
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
