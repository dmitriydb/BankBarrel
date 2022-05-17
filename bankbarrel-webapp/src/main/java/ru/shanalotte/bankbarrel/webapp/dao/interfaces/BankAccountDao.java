package ru.shanalotte.bankbarrel.webapp.dao.interfaces;

import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.webapp.dto.transfer.TransferDto;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotFound;

/**
 * DAO для объектов класса BankAccount.
 */
public interface BankAccountDao {

  void save(BankAccountDto account);

  BankAccountDto findByNumber(String number);

  void delete(BankAccountDto account);

  BankAccountDto findByTransferDto(TransferDto dto) throws BankAccountNotFound;
}
