package ru.shanalotte.bankbarrel.webapp.dao.interfaces;

import ru.shanalotte.bankbarrel.core.domain.BankClient;

/**
 * DAO для объектов класса BankClient.
 */
public interface BankClientDao {
  int count();

  void save(BankClient newBankClient);

  BankClient findByGivenName(String givenName);
}
