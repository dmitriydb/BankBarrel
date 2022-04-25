package ru.shanalotte.bankbarrel.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.shanalotte.bankbarrel.webapp.dao.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.AccountOpeningDto;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.AccountAdditionalTypesListingService;
import ru.shanalotte.bankbarrel.webapp.service.AccountOpeningCurrenciesListingService;
import ru.shanalotte.bankbarrel.webapp.service.AccountTypeListingService;

/**
 * Controller for the user page.
 */
@Controller
public class UserPageController {

  private WebAppUserDao webAppUserDao;
  private AccountTypeListingService accountTypeListingService;
  private AccountAdditionalTypesListingService accountAdditionalTypesListingService;
  private AccountOpeningCurrenciesListingService accountOpeningCurrenciesListingService;

  public UserPageController(WebAppUserDao webAppUserDao,
                            AccountTypeListingService accountTypeListingService,
                            AccountAdditionalTypesListingService
                                accountAdditionalTypesListingService,
                            AccountOpeningCurrenciesListingService
                                accountOpeningCurrenciesListingService) {
    this.webAppUserDao = webAppUserDao;
    this.accountTypeListingService = accountTypeListingService;
    this.accountAdditionalTypesListingService = accountAdditionalTypesListingService;
    this.accountOpeningCurrenciesListingService = accountOpeningCurrenciesListingService;
  }

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
    return "user-page";
  }
}
