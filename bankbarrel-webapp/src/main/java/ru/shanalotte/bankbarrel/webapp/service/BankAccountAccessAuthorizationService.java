package ru.shanalotte.bankbarrel.webapp.service;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.BankClient;

/**
 * Сервис, который выдает разрешения клиентам банка на доступ к банковским счетам.
 */
@Service
public class BankAccountAccessAuthorizationService {

  /**
   * Определяет, есть ли у клиента банка счет с заданным номером.
   * @param accountNumber искомый номер счета
   */
  public boolean bankClientHasTheAccountWithNumber(BankClient client, String accountNumber) {
    return client.getAccounts().stream()
        .anyMatch(account -> account.getIdentifier().equals(accountNumber));
  }
}
