package ru.shanalotte.bankbarrel.webapp.controller.account;

import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;
import ru.shanalotte.bankbarrel.core.service.BankService;
import ru.shanalotte.bankbarrel.core.service.IBankService;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotExists;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotFound;
import ru.shanalotte.bankbarrel.webapp.exception.UnathorizedAccessToBankAccount;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.BankAccountAccessAuthorizationService;
import ru.shanalotte.bankbarrel.webapp.service.IBankAccountAccessAuthorizationService;
import ru.shanalotte.bankbarrel.webapp.service.IWebAppBankService;
import ru.shanalotte.bankbarrel.webapp.service.WebAppBankService;

@Controller
public class DepositController {

  private IBankAccountAccessAuthorizationService bankAccountAccessAuthorizationService;
  private IWebAppBankService bankService;


  public DepositController(IBankAccountAccessAuthorizationService bankAccountAccessAuthorizationService, IWebAppBankService bankService) {
    this.bankAccountAccessAuthorizationService = bankAccountAccessAuthorizationService;
    this.bankService = bankService;
  }

  @PostMapping("/account/{number}/deposit")
  public String depositAccount(@PathVariable("number") String accountNumber,
                               @RequestParam("username") String username,
                               @RequestParam("amount") Double amount,
                               @RequestParam("currency") String currency
                               ) throws WebAppUserNotFound,
      UnathorizedAccessToBankAccount, BankAccountNotExists, UnknownCurrencyRate {
    BankAccountDto account = bankAccountAccessAuthorizationService.authorize(username, accountNumber);
    MonetaryAmount monetaryAmount = new MonetaryAmount(amount, currency);
    bankService.deposit(account, monetaryAmount);
    return "redirect:/user/" + username + "/account/" + accountNumber;
  }
}
