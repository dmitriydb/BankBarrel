package ru.shanalotte.bankbarrel.webapp.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;
import ru.shanalotte.bankbarrel.core.service.BankService;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotExists;
import ru.shanalotte.bankbarrel.webapp.exception.UnathorizedAccessToBankAccount;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.BankAccountAccessAuthorizationService;

@Controller
public class WithdrawController {

  private BankAccountAccessAuthorizationService bankAccountAccessAuthorizationService;
  private BankService bankService;

  public WithdrawController(BankAccountAccessAuthorizationService bankAccountAccessAuthorizationService, BankService bankService) {
    this.bankAccountAccessAuthorizationService = bankAccountAccessAuthorizationService;
    this.bankService = bankService;
  }

  @PostMapping("/account/{number}/withdraw")
  public String depositAccount(@PathVariable("number") String accountNumber,
                               @RequestParam("username") String username,
                               @RequestParam("amount") Double amount,
                               @RequestParam("currency") String currency,
                               RedirectAttributes redirectAttributes
  ) throws WebAppUserNotFound,
      UnathorizedAccessToBankAccount, BankAccountNotExists {
    BankAccountDto account = bankAccountAccessAuthorizationService.authorize(username, accountNumber);
    MonetaryAmount monetaryAmount = new MonetaryAmount(amount, currency);
    //  bankService.withdraw(account, monetaryAmount);
    //TODO
    return "redirect:/user/" + username + "/account/" + accountNumber;
  }
}
