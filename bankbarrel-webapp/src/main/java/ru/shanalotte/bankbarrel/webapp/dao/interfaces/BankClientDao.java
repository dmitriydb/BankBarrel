package ru.shanalotte.bankbarrel.webapp.dao.interfaces;

import java.util.List;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;

/**
 * DAO для объектов класса BankClient.
 */
public interface BankClientDao {
  int count();

  void save(BankClientDto newBankClient);

  BankClientDto findByGivenName(String givenName);

  List<BankAccountDto> accounts(BankClientDto clientDto);

  Long idByDto(BankClientDto dto);
}
