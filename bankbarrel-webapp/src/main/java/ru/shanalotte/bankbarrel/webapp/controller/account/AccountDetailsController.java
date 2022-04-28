package ru.shanalotte.bankbarrel.webapp.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankAccountDao;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotExists;
import ru.shanalotte.bankbarrel.webapp.exception.UnathorizedAccessToBankAccount;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.BankAccountAccessAuthorizationService;
import ru.shanalotte.bankbarrel.webapp.service.converter.BankAccountDetailsDtoConverter;

/**
 * Контроллер, который обрабатывает запрос на открытие страницы с деталями конкретного счёта.
 */
@Controller
public class AccountDetailsController {

  private WebAppUserDao webAppUserDao;
  private BankAccountDao bankAccountDao;
  private BankAccountAccessAuthorizationService bankAccountAccessAuthorizationService;
  private BankAccountDetailsDtoConverter bankAccountDetailsDtoConverter;

  /**
   * Конструктор со всеми зависимостями.
   */
  public AccountDetailsController(WebAppUserDao webAppUserDao,
                                  BankAccountDao bankAccountDao,
                                  BankAccountAccessAuthorizationService
                                      bankAccountAccessAuthorizationService,
                                  BankAccountDetailsDtoConverter bankAccountDetailsDtoConverter) {
    this.webAppUserDao = webAppUserDao;
    this.bankAccountDao = bankAccountDao;
    this.bankAccountAccessAuthorizationService = bankAccountAccessAuthorizationService;
    this.bankAccountDetailsDtoConverter = bankAccountDetailsDtoConverter;
  }

  /**
   * Обрабатывает запрос на открытие страницы с информацией о банковском счете.
   *
   * @param accountNumber номер счета
   * @param username      имя пользователя
   * @throws WebAppUserNotFound             если пользователь не авторизован
   * @throws UnathorizedAccessToBankAccount <p>если пользователь пытается получить
   *                                        * доступ к чужому счету</p>
   * @throws BankAccountNotExists           если счет с таким номером не существует
   */
  @GetMapping("/user/{username}/account/{number}")
  public String openAccountPage(Model model, @PathVariable("number") String accountNumber,
                                @PathVariable("username") String username)
      throws WebAppUserNotFound, UnathorizedAccessToBankAccount, BankAccountNotExists {

    if (!webAppUserDao.isUserExists(username)) {
      throw new WebAppUserNotFound(username);
    }

    if (bankAccountDao.findByNumber(accountNumber) == null) {
      throw new BankAccountNotExists(username + " to " + accountNumber);
    }

    BankClient bankClient = webAppUserDao.findByUsername(username).getClient();
    boolean isAuthorized = bankAccountAccessAuthorizationService
        .bankClientHasTheAccountWithNumber(bankClient, accountNumber);

    if (isAuthorized) {
      BankAccount bankAccount = bankAccountDao.findByNumber(accountNumber);
      model.addAttribute("account", bankAccountDetailsDtoConverter.convert(bankAccount));
      return "account";
    } else {
      throw new UnathorizedAccessToBankAccount(username + " to " + accountNumber);
    }
  }

}