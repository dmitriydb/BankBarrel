package ru.shanalotte.bankbarrel.webapp.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotExists;
import ru.shanalotte.bankbarrel.webapp.exception.UnathorizedAccessToBankAccount;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.BankAccountAccessAuthorizationService;
import ru.shanalotte.bankbarrel.webapp.service.WebAppBankService;

/**
 * Контроллер для операций денежных вкладов в веб-приложении.
 */
@Controller
public class DepositController {

  private BankAccountAccessAuthorizationService bankAccountAccessAuthorizationService;
  private WebAppBankService bankService;

  /**
   * Конструктор со всеми зависимостями.
   */
  public DepositController(BankAccountAccessAuthorizationService
                               bankAccountAccessAuthorizationService,
                           WebAppBankService bankService) {
    this.bankAccountAccessAuthorizationService = bankAccountAccessAuthorizationService;
    this.bankService = bankService;
  }


  /**
   * Обработчик денежного вклада на счет через веб-приложение.
   */
  @PostMapping("/account/{number}/deposit")
  public String depositAccount(@PathVariable("number") String accountNumber,
                               @RequestParam("username") String username,
                               @RequestParam("amount") Double amount,
                               @RequestParam("currency") String currency
  ) throws WebAppUserNotFound,
      UnathorizedAccessToBankAccount, BankAccountNotExists, UnknownCurrencyRate {
    BankAccountDto account = bankAccountAccessAuthorizationService
        .authorize(username, accountNumber);
    MonetaryAmount monetaryAmount = new MonetaryAmount(amount, currency);
    bankService.deposit(account, monetaryAmount);
    return "redirect:/user/" + username + "/account/" + accountNumber;
  }
}
