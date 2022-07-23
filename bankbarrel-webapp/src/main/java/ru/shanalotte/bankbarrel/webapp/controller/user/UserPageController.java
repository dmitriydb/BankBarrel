package ru.shanalotte.bankbarrel.webapp.controller.user;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankClientDao;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.account.AccountOpeningDto;
import ru.shanalotte.bankbarrel.webapp.dto.account.BankAccountWebAppDto;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.converter.BankAccountDtoConverter;
import ru.shanalotte.bankbarrel.webapp.service.listing.AccountAdditionalTypesListingService;
import ru.shanalotte.bankbarrel.webapp.service.listing.AccountOpeningCurrenciesListingService;
import ru.shanalotte.bankbarrel.webapp.service.listing.AccountTypeListingService;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

/**
 * Controller for the user page.
 */
@Controller
public class UserPageController {


  @Value("${server.port}")
  private String serverPort;

  private WebAppUserDao webAppUserDao;
  private BankClientDao bankClientDao;
  private AccountTypeListingService accountTypeListingService;
  private AccountAdditionalTypesListingService accountAdditionalTypesListingService;
  private AccountOpeningCurrenciesListingService accountOpeningCurrenciesListingService;
  private BankAccountDtoConverter bankAccountDtoConverter;

  private static final Logger logger = LoggerFactory.getLogger(UserPageController.class);

  /**
   * Конструктор со всеми зависимостями.
   */
  public UserPageController(
      WebAppUserDao webAppUserDao, BankClientDao bankClientDao,
      AccountTypeListingService accountTypeListingService,
      AccountAdditionalTypesListingService accountAdditionalTypesListingService,
      AccountOpeningCurrenciesListingService accountOpeningCurrenciesListingService,
      BankAccountDtoConverter bankAccountDtoConverter) {
    this.serverPort = serverPort;
    this.webAppUserDao = webAppUserDao;
    this.bankClientDao = bankClientDao;
    this.accountTypeListingService = accountTypeListingService;
    this.accountAdditionalTypesListingService = accountAdditionalTypesListingService;
    this.accountOpeningCurrenciesListingService = accountOpeningCurrenciesListingService;
    this.bankAccountDtoConverter = bankAccountDtoConverter;
  }

  /**
   * Вход в личный кабинет пользователя.
   *
   * @throws WebAppUserNotFound если такой пользователь не существует
   */
  @GetMapping("/user/{username}")
  public String userPage(@PathVariable("username") String username, Model model)
      throws WebAppUserNotFound {
    logger.info("Пользователь {} заходит в свой личный кабинет", username);
    if (!webAppUserDao.isUserExists(username)) {
      logger.warn("Пользователь {} не существует", username);
      throw new WebAppUserNotFound(username);
    }
    model.addAttribute("serverPort", serverPort);
    model.addAttribute("username", username);
    model.addAttribute("accountOpeningDto", new AccountOpeningDto());
    model.addAttribute("accountTypesDto", accountTypeListingService.getListingDto());
    model.addAttribute("accountAdditionalTypesDto",
        accountAdditionalTypesListingService.getListingDto());
    model.addAttribute("accountOpeningCurrenciesDto",
        accountOpeningCurrenciesListingService.getListingDto());
    WebAppUser webAppUser = webAppUserDao.findByUsername(username);
    List<BankAccountWebAppDto> accountDtos = bankClientDao.accounts(webAppUser.getClient())
            .stream().map(dto -> bankAccountDtoConverter.convert(dto)).collect(Collectors.toList());
    model.addAttribute("accounts", accountDtos);
    return "user-page";
  }
}
