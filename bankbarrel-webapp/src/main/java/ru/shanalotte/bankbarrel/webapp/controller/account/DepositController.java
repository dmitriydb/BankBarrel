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
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotExists;
import ru.shanalotte.bankbarrel.webapp.exception.UnathorizedAccessToBankAccount;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.BankAccountAccessAuthorizationService;

@Controller
public class DepositController {

  private BankAccountAccessAuthorizationService bankAccountAccessAuthorizationService;
  private BankService bankService;

  public DepositController(BankAccountAccessAuthorizationService bankAccountAccessAuthorizationService, BankService bankService) {
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
    //bankService.deposit(account, monetaryAmount);
    //TODO
    return "redirect:/user/" + username + "/account/" + accountNumber;
  }
}
