package ru.shanalotte.bankbarrel.webapp.controller.user;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.account.AccountOpeningDto;
import ru.shanalotte.bankbarrel.webapp.dto.account.BankAccountDto;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.listing.AccountAdditionalTypesListingService;
import ru.shanalotte.bankbarrel.webapp.service.listing.AccountOpeningCurrenciesListingService;
import ru.shanalotte.bankbarrel.webapp.service.listing.AccountTypeListingService;
import ru.shanalotte.bankbarrel.webapp.service.converter.BankAccountDtoConverter;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

/**
 * Controller for the user page.
 */
@Controller
public class UserPageController {

  private WebAppUserDao webAppUserDao;
  private AccountTypeListingService accountTypeListingService;
  private AccountAdditionalTypesListingService accountAdditionalTypesListingService;
  private AccountOpeningCurrenciesListingService accountOpeningCurrenciesListingService;
  private BankAccountDtoConverter bankAccountDtoConverter;

  /**
   * Конструктор со всеми зависимостями.
   */
  public UserPageController(WebAppUserDao webAppUserDao,
                            AccountTypeListingService accountTypeListingService,
                            AccountAdditionalTypesListingService accountAdditionalTypesListingService,
                            AccountOpeningCurrenciesListingService accountOpeningCurrenciesListingService,
                            BankAccountDtoConverter bankAccountDtoConverter) {
    this.webAppUserDao = webAppUserDao;
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
    if (!webAppUserDao.isUserExists(username)) {
      throw new WebAppUserNotFound(username);
    }
    model.addAttribute("username", username);
    model.addAttribute("accountOpeningDto", new AccountOpeningDto());
    model.addAttribute("accountTypesDto", accountTypeListingService.getListingDto());
    model.addAttribute("accountAdditionalTypesDto",
        accountAdditionalTypesListingService.getListingDto());
    model.addAttribute("accountOpeningCurrenciesDto",
        accountOpeningCurrenciesListingService.getListingDto());
    WebAppUser webAppUser = webAppUserDao.findByUsername(username);
    List<BankAccountDto> accountDtos = webAppUser.getClient().getAccounts()
        .stream().map(account -> bankAccountDtoConverter.convert(account))
        .collect(Collectors.toList());
    model.addAttribute("accounts", accountDtos);
    return "user-page";
  }
}
