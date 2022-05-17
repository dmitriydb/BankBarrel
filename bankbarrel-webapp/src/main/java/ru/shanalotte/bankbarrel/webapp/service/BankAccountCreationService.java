package ru.shanalotte.bankbarrel.webapp.service;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.config.FakeAccountNumberGenerator;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankAccountDao;
import ru.shanalotte.bankbarrel.webapp.dto.account.AccountOpeningDto;
import ru.shanalotte.bankbarrel.webapp.service.converter.AccountTypesNameConverter;

/**
 * Сервис, который открывает новые банковские счета.
 */
@Service
public class BankAccountCreationService {

  private AccountTypesNameConverter accountTypesNameConverter;
  private BankAccountDao bankAccountDao;
  private FakeAccountNumberGenerator fakeAccountNumberGenerator;

  public BankAccountCreationService(AccountTypesNameConverter accountTypesNameConverter,
                                    BankAccountDao bankAccountDao,
                                    FakeAccountNumberGenerator fakeAccountNumberGenerator) {
    this.accountTypesNameConverter = accountTypesNameConverter;
    this.bankAccountDao = bankAccountDao;
    this.fakeAccountNumberGenerator = fakeAccountNumberGenerator;
  }

  /**
   * Открывает счет с информацией из дто и привязывает его клиенту.
   */
  public void createAccount(AccountOpeningDto dto, BankClientDto bankClient) {

    BankAccountDto bankAccount = new BankAccountDto();
    bankAccount.setNumber(fakeAccountNumberGenerator.generateNumber());
    bankAccount.setDescription(bankAccount.getNumber());
    bankAccount.setCurrency(dto.getCurrency());
    bankAccount.setType(dto.getAccountType());
    bankAccount.setAdditionalType(dto.getAccountAdditionalType());
    bankAccountDao.save(bankAccount);
  }
}
