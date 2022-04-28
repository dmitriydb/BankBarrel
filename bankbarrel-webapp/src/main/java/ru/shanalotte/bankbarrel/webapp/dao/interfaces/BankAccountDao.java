package ru.shanalotte.bankbarrel.webapp.dao.interfaces;

import ru.shanalotte.bankbarrel.core.domain.BankAccount;

/**
 * DAO для объектов класса BankAccount;
 */
public interface BankAccountDao {

  void save(BankAccount account);
  BankAccount findByNumber(String number);
}
