package ru.shanalotte.bankbarrel.webapp.service;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankAccountDao;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotExists;
import ru.shanalotte.bankbarrel.webapp.exception.UnathorizedAccessToBankAccount;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

/**
 * Сервис, который выдает разрешения клиентам банка на доступ к банковским счетам.
 */
@Service
public class BankAccountAccessAuthorizationService {

  private WebAppUserDao webAppUserDao;
  private BankAccountDao bankAccountDao;

  public BankAccountAccessAuthorizationService(WebAppUserDao webAppUserDao, BankAccountDao bankAccountDao) {
    this.webAppUserDao = webAppUserDao;
    this.bankAccountDao = bankAccountDao;
  }

  /**
   * Определяет, есть ли у клиента банка счет с заданным номером.
   *
   * @param client
   * @param accountNumber искомый номер счета
   */
  public boolean bankClientHasTheAccountWithNumber(BankClientDto client, String accountNumber) {
    //TODO
    return true;
  }

  /**
   * Авторизует доступ пользователя веб-приложения к банковскому счету
   * и возвращает банковского клиента в успешном случае.
   *
   * @param username логин пользователя веб-приложения
   * @param accountNumber номер банковского счета
   * @return
   */
  public BankAccountDto authorize(String username, String accountNumber) throws WebAppUserNotFound, BankAccountNotExists, UnathorizedAccessToBankAccount {
    WebAppUser webAppUser = webAppUserDao.findByUsername(username);
    if (webAppUser == null) {
      throw new WebAppUserNotFound(username);
    }
    BankAccountDto bankAccount = bankAccountDao.findByNumber(accountNumber);
    if (bankAccount == null) {
      throw new BankAccountNotExists(accountNumber);
    }
    BankClientDto client = webAppUser.getClient();
    if (!bankClientHasTheAccountWithNumber(client, accountNumber)) {
      throw new UnathorizedAccessToBankAccount(client + " " + accountNumber);
    }
    return bankAccount;
  }
}
