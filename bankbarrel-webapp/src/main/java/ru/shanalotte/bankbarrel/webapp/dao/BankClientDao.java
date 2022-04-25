package ru.shanalotte.bankbarrel.webapp.dao;

import ru.shanalotte.bankbarrel.core.BankClient;

/**
 * DAO для объектов класса BankClient.
 */
public interface BankClientDao {
  int count();

  void save(BankClient newBankClient);

  BankClient findByGivenName(String givenName);
}
