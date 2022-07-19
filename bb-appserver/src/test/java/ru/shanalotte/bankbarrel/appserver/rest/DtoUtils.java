package ru.shanalotte.bankbarrel.appserver.rest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.appserver.TestUtils;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountDao;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.dto.AccountAdditionalTypeDto;
import ru.shanalotte.bankbarrel.core.dto.AccountTypeDto;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.core.dto.CurrencyDto;
import ru.shanalotte.bankbarrel.core.dto.CurrencyRateDto;
import ru.shanalotte.bankbarrel.core.dto.DepositDto;
import ru.shanalotte.bankbarrel.core.dto.TransferDto;
import ru.shanalotte.bankbarrel.core.dto.WithdrawDto;

@Service
public class DtoUtils {

  @Autowired
  private TestUtils testUtils;

  @Autowired
  private BankAccountDao bankAccountDao;

  public BankAccountDto generateRandomBankAccountDto() {
    BankAccountDto bankAccountDto = new BankAccountDto();
    bankAccountDto.setBalance("100");
    bankAccountDto.setCurrency("USD");
    bankAccountDto.setType("CHECKING");
    bankAccountDto.setAdditionalType("PREMIUM");
    bankAccountDto.setOwner(1L);
    bankAccountDto.setDescription("test");
    bankAccountDto.setNumber(testUtils.randomValue());
    return bankAccountDto;
  }

  public AccountAdditionalTypeDto generateRandomAdditionalAccountTypeDto() {
    AccountAdditionalTypeDto accountAdditionalTypeDto = new AccountAdditionalTypeDto();
    accountAdditionalTypeDto.setType(testUtils.randomValue());
    accountAdditionalTypeDto.setDescription(testUtils.randomValue());
    return accountAdditionalTypeDto;
  }

  public AccountTypeDto generateRandomBankTypeDto() {
    AccountTypeDto dto = new AccountTypeDto();
    dto.setType(testUtils.randomValue());
    dto.setDescription(testUtils.randomValue());
    return dto;
  }

  public BankClientDto generateBankClientDto() {
    BankClientDto dto = new BankClientDto();
    dto.setGivenName(testUtils.randomValue());
    dto.setFamilyName(testUtils.randomValue());
    dto.setEmail(testUtils.randomValue());
    dto.setTelephone(testUtils.randomValue());
    return dto;
  }

  public CurrencyDto generateCurrencyDto() {
    CurrencyDto currencyDto = new CurrencyDto();
    currencyDto.setCode(testUtils.randomValue());
    return currencyDto;
  }

  public CurrencyRateDto generateCurrencyRateDto(String currencyCode) {
    CurrencyRateDto currencyRateDto = new CurrencyRateDto();
    currencyRateDto.setRate(BigDecimal.TEN);
    currencyRateDto.setCurrency(currencyCode);
    currencyRateDto.setMore(false);
    return currencyRateDto;
  }

  public DepositDto generateDepositDto () {
    BankAccount account = bankAccountDao.findAll().stream().filter(acc -> acc.getCurrency().equals("USD")).findFirst().get();
    DepositDto depositDto = new DepositDto();
    depositDto.setAccount(account.getIdentifier());
    depositDto.setAmount(BigDecimal.valueOf(10));
    depositDto.setCurrency("USD");
    depositDto.setSource("curl");
    depositDto.setTimestamp(String.valueOf(Timestamp.valueOf(LocalDateTime.now())));
    return depositDto;
  }

  public WithdrawDto generateWithdrawDto() {
    BankAccount account = bankAccountDao.findAll().stream().filter(acc -> acc.getCurrency().equals("USD")).findFirst().get();
    WithdrawDto withdrawDto = new WithdrawDto();
    withdrawDto.setAccount(account.getIdentifier());
    withdrawDto.setAmount(BigDecimal.valueOf(10));
    withdrawDto.setCurrency("USD");
    withdrawDto.setSource("curl");
    withdrawDto.setTimestamp(String.valueOf(Timestamp.valueOf(LocalDateTime.now())));
    return withdrawDto;
  }

  public TransferDto generateTransferDto() {
    BankAccount account = bankAccountDao.findAll().stream().filter(acc -> acc.getCurrency().equals("USD")).findFirst().get();
    TransferDto transferDto = new TransferDto();
    transferDto.setToAccount(account.getIdentifier());
    transferDto.setFromAccount(account.getIdentifier());
    transferDto.setAmount(BigDecimal.valueOf(1));
    transferDto.setCurrency("USD");
    transferDto.setSource("curl");
    transferDto.setTimestamp(String.valueOf(Timestamp.valueOf(LocalDateTime.now())));
    return transferDto;
  }
}
