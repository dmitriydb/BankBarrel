package ru.shanalotte.bankbarrel.appserver.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OperationSourceDaoTest {

  @Autowired
  private OperationSourceDao operationSourceDao;

  @Autowired
  private CurrencyDao currencyDao;

  @Autowired
  private CurrencyRateDao currencyRateDao;

  @Autowired
  private BankAccountTypeDao bankAccountTypeDao;

  @Autowired
  private BankAccountDao bankAccountDao;

  @Test
  public void tryLoadSome() {
    System.out.println(bankAccountDao.findAll().size());
  }
}