package ru.shanalotte.bankbarrel.webapp.controller.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankAccountDao;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.transfer.TransferDto;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotExists;
import ru.shanalotte.bankbarrel.webapp.exception.UnathorizedAccessToBankAccount;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.BankAccountAccessAuthorizationService;
import ru.shanalotte.bankbarrel.webapp.service.converter.BankAccountDetailsDtoConverter;
import ru.shanalotte.bankbarrel.webapp.service.listing.AccountOpeningCurrenciesListingService;

/**
 * Контроллер, который обрабатывает запрос на открытие страницы с деталями конкретного счёта.
 */
@Controller
public class AccountDetailsController {

  private WebAppUserDao webAppUserDao;
  private BankAccountDao bankAccountDao;
  private BankAccountAccessAuthorizationService bankAccountAccessAuthorizationService;
  private BankAccountDetailsDtoConverter bankAccountDetailsDtoConverter;
  private AccountOpeningCurrenciesListingService accountOpeningCurrenciesListingService;
  private static final Logger logger = LoggerFactory.getLogger(AccountDetailsController.class);

  /**
   * Конструктор со всеми зависимостями.
   */
  public AccountDetailsController(WebAppUserDao webAppUserDao,
                                  BankAccountDao bankAccountDao,
                                  BankAccountAccessAuthorizationService
                                      bankAccountAccessAuthorizationService,
                                  BankAccountDetailsDtoConverter bankAccountDetailsDtoConverter,
                                  AccountOpeningCurrenciesListingService
                                      accountOpeningCurrenciesListingService) {
    this.webAppUserDao = webAppUserDao;
    this.bankAccountDao = bankAccountDao;
    this.bankAccountAccessAuthorizationService = bankAccountAccessAuthorizationService;
    this.bankAccountDetailsDtoConverter = bankAccountDetailsDtoConverter;
    this.accountOpeningCurrenciesListingService = accountOpeningCurrenciesListingService;
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
    logger.info("Пользователь {} пытается открыть страницу счета {}", username, accountNumber);

    if (!webAppUserDao.isUserExists(username)) {
      logger.warn("Пользователь с именем {} не существует", username);
      throw new WebAppUserNotFound(username);
    }

    if (bankAccountDao.findByNumber(accountNumber) == null) {
      logger.warn("Счет с номером {} не существует", accountNumber);
      throw new BankAccountNotExists(username + " to " + accountNumber);
    }

    BankClientDto bankClient = webAppUserDao.findByUsername(username).getClient();
    boolean isAuthorized = bankAccountAccessAuthorizationService
        .bankClientHasTheAccountWithNumber(bankClient, accountNumber);

    if (isAuthorized) {
      BankAccountDto bankAccount = bankAccountDao.findByNumber(accountNumber);
      model.addAttribute("account", bankAccountDetailsDtoConverter.convert(bankAccount));
      model.addAttribute("currencies",
          accountOpeningCurrenciesListingService.getListingDto().getCodeAndValuePairs());
      model.addAttribute("transferDto", new TransferDto());
      return "account";
    } else {
      logger.warn("У пользователя {} нет доступа к счету {}", username, accountNumber);
      throw new UnathorizedAccessToBankAccount(username + " to " + accountNumber);
    }
  }

}
