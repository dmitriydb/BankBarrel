package ru.shanalotte.bankbarrel.webapp.dao.interfaces;

import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.webapp.dto.transfer.TransferDto;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotFound;

/**
 * DAO для объектов класса BankAccount.
 */
public interface BankAccountDao {

  void save(BankAccount account);

  BankAccount findByNumber(String number);

  void delete(BankAccount account);

  BankAccount findByTransferDto(TransferDto dto) throws BankAccountNotFound;
}
