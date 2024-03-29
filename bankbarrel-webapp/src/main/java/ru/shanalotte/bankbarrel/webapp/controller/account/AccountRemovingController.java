package ru.shanalotte.bankbarrel.webapp.controller.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankAccountDao;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotExists;
import ru.shanalotte.bankbarrel.webapp.exception.UnathorizedAccessToBankAccount;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.BankAccountAccessAuthorizationService;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

/**
 * Контроллер, который обрабатывает запросы на закрытие банковских счетов.
 */
@Controller
public class AccountRemovingController {

  private WebAppUserDao webAppUserDao;
  private BankAccountAccessAuthorizationService bankAccountAccessAuthorizationService;
  private BankAccountDao bankAccountDao;

  private static final Logger logger = LoggerFactory.getLogger(AccountRemovingController.class);
  /**
   * Конструктор со всеми зависимостями.
   */

  public AccountRemovingController(WebAppUserDao webAppUserDao,
                                   BankAccountAccessAuthorizationService
                                       bankAccountAccessAuthorizationService,
                                   BankAccountDao bankAccountDao) {
    this.webAppUserDao = webAppUserDao;
    this.bankAccountAccessAuthorizationService = bankAccountAccessAuthorizationService;
    this.bankAccountDao = bankAccountDao;
  }

  /**
   * Обрабатывает запрос на удаление счета по номеру.
   *
   * @throws WebAppUserNotFound в случае, если запрос
   *                                        был отправлен несуществующим пользователем
   * @throws BankAccountNotExists в случае, если счет с таким номером не существует
   * @throws UnathorizedAccessToBankAccount в случае,
   *                                        если у пользователя не доступа к счету с таким номером
   */
  @PostMapping("/account/{number}/delete")
  public String removeAccount(@PathVariable("number") String accountNumber,
                              @RequestParam("username") String username,
                              Model model)
      throws WebAppUserNotFound, BankAccountNotExists, UnathorizedAccessToBankAccount {
    logger.info("Пользователь с именем {} пытается удалить счет {}", username, accountNumber);
    if (!webAppUserDao.isUserExists(username)) {
      logger.warn("Пользователь с именем {} не существует", username);
      throw new WebAppUserNotFound(username);
    }
    WebAppUser webAppUser = webAppUserDao.findByUsername(username);
    BankClientDto bankClient = webAppUser.getClient();
    BankAccountDto account = bankAccountDao.findByNumber(accountNumber);
    if (account == null) {
      logger.warn("Счет с номером {} не существует", accountNumber);
      throw new BankAccountNotExists(accountNumber);
    }
    if (!bankAccountAccessAuthorizationService
        .bankClientHasTheAccountWithNumber(bankClient, accountNumber)) {
      logger.warn("У пользователя {} нет доступа к счету {}", username, accountNumber);
      throw new UnathorizedAccessToBankAccount(username + " to " + accountNumber);
    }
    bankAccountDao.delete(account);
    return "redirect:/user/" + username;
  }
}
