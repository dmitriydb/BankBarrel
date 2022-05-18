package ru.shanalotte.bankbarrel.webapp.service;

import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotExists;
import ru.shanalotte.bankbarrel.webapp.exception.UnathorizedAccessToBankAccount;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;

public interface IBankAccountAccessAuthorizationService {

  boolean bankClientHasTheAccountWithNumber(BankClientDto client, String accountNumber);

  BankAccountDto authorize(String username, String accountNumber) throws WebAppUserNotFound, BankAccountNotExists, UnathorizedAccessToBankAccount;

}
