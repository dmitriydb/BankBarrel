package ru.shanalotte.bankbarrel.webapp.dao.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankAccountDao;
import ru.shanalotte.bankbarrel.webapp.dto.transfer.TransferDto;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotFound;

@Repository
@Primary
public class RealBankAccountDao implements BankAccountDao {

  @Override
  public void save(BankAccountDto account) {

  }

  @Override
  public BankAccountDto findByNumber(String number) {
    return null;
  }

  @Override
  public void delete(BankAccountDto account) {

  }

  @Override
  public BankAccountDto findByTransferDto(TransferDto dto) throws BankAccountNotFound {
    return null;
  }
}
