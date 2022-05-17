package ru.shanalotte.bankbarrel.webapp.dao.interfaces;

import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;

/**
 * DAO для объектов класса BankClient.
 */
public interface BankClientDao {
  int count();

  void save(BankClientDto newBankClient);

  BankClientDto findByGivenName(String givenName);
}
