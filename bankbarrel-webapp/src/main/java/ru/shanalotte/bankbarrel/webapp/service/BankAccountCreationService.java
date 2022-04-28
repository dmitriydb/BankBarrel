package ru.shanalotte.bankbarrel.webapp.service;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
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

  public BankAccountCreationService(AccountTypesNameConverter accountTypesNameConverter, BankAccountDao bankAccountDao) {
    this.accountTypesNameConverter = accountTypesNameConverter;
    this.bankAccountDao = bankAccountDao;
  }

  /**
   * Открывает счет с информацией из дто и привязывает его клиенту.
   */
  public void createAccount(AccountOpeningDto dto, BankClient bankClient) {
    BankAccount bankAccount = new BankAccount.Builder()
        .withType(accountTypesNameConverter.getAccountType(dto.getAccountType()))
        .withAdditionalType(accountTypesNameConverter.getAccountAdditionalType(
            dto.getAccountAdditionalType()))
        .withCurrency(dto.getCurrency())
        .withOwner(bankClient)
        .build();
    bankAccountDao.save(bankAccount);
    bankClient.addAccount(bankAccount);
  }
}
