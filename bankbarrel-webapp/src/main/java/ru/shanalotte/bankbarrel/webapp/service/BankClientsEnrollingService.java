package ru.shanalotte.bankbarrel.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.BankAccount;
import ru.shanalotte.bankbarrel.core.BankAccountAdditionalType;
import ru.shanalotte.bankbarrel.core.BankClient;
import ru.shanalotte.bankbarrel.webapp.dao.BankClientDao;
import ru.shanalotte.bankbarrel.webapp.dto.BankClientInfoDto;

/**
 * Сервис, который создает и сохраняет новых банковских клиентов.
 */
@Service
public class BankClientsEnrollingService {

  @Autowired
  private BankClientDao bankClientDao;

  /**
   * Получает DTO из контроллера в момент заполнения пользователем клиентом
   * веб-приложения формы регистрации.
   * Должен создать объект класса BankClient и сохранить его.
   *
   * @see ru.shanalotte.bankbarrel.webapp.dto.BankClientInfoDto
   * @see ru.shanalotte.bankbarrel.core.BankClient
   * @param dto DTO
   * @return созданный объект класса BankClient, если создание прошло успешно
   */
  public BankClient enrollClient(BankClientInfoDto dto) {
    BankClient newBankClient = new BankClient.Builder(dto.getFirstName(), dto.getLastName())
        .withEmail(dto.getEmail())
        .withTelephone(dto.getTelephone())
        .build();
    bankClientDao.save(newBankClient);
    return newBankClient;
  }
}
