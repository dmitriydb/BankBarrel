package ru.shanalotte.bankbarrel.webapp.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankAccountDao;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotExists;
import ru.shanalotte.bankbarrel.webapp.exception.UnathorizedAccessToBankAccount;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.BankAccountAccessAuthorizationService;
import ru.shanalotte.bankbarrel.webapp.service.IBankAccountAccessAuthorizationService;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

@Controller
public class AccountRemovingController {

  private WebAppUserDao webAppUserDao;
  private IBankAccountAccessAuthorizationService bankAccountAccessAuthorizationService;
  private BankAccountDao bankAccountDao;

  public AccountRemovingController(WebAppUserDao webAppUserDao, IBankAccountAccessAuthorizationService bankAccountAccessAuthorizationService, BankAccountDao bankAccountDao) {
    this.webAppUserDao = webAppUserDao;
    this.bankAccountAccessAuthorizationService = bankAccountAccessAuthorizationService;
    this.bankAccountDao = bankAccountDao;
  }

  @PostMapping("/account/{number}/delete")
  public String removeAccount(@PathVariable("number") String accountNumber, @RequestParam("username") String username, Model model) throws WebAppUserNotFound, BankAccountNotExists, UnathorizedAccessToBankAccount {
    if (!webAppUserDao.isUserExists(username)) {
      throw new WebAppUserNotFound(username);
    }
    WebAppUser webAppUser = webAppUserDao.findByUsername(username);
    BankClientDto bankClient = webAppUser.getClient();
    BankAccountDto account = bankAccountDao.findByNumber(accountNumber);
    if (account == null) {
      throw new BankAccountNotExists(accountNumber);
    }
    if (!bankAccountAccessAuthorizationService.bankClientHasTheAccountWithNumber(bankClient, accountNumber)) {
      throw new UnathorizedAccessToBankAccount(username + " to " + accountNumber);
    }
    bankAccountDao.delete(account);
    return "redirect:/user/" + username;
  }
}
