package ru.shanalotte.bankbarrel.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.BankAccount;
import ru.shanalotte.bankbarrel.core.BankAccountAdditionalType;
import ru.shanalotte.bankbarrel.core.BankClient;
import ru.shanalotte.bankbarrel.webapp.dao.BankClientDao;
import ru.shanalotte.bankbarrel.webapp.dto.BankClientInfoDto;

@Service
public class BankClientsEnrollingService {

  @Autowired
  private BankClientDao bankClientDao;

  public BankClient enrollClient(BankClientInfoDto dto) {
    BankClient newBankClient = new BankClient.Builder(dto.getFirstName(), dto.getLastName())
        .withEmail(dto.getEmail())
        .withTelephone(dto.getTelephone())
        .build();
    bankClientDao.save(newBankClient);
    return newBankClient;
  }
}
