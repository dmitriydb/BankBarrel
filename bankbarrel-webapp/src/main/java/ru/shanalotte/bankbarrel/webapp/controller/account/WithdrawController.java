package ru.shanalotte.bankbarrel.webapp.controller.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotExists;
import ru.shanalotte.bankbarrel.webapp.exception.UnathorizedAccessToBankAccount;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.BankAccountAccessAuthorizationService;
import ru.shanalotte.bankbarrel.webapp.service.WebAppBankService;

/**
 * Контроллер снятия средств в веб-приложении.
 */
@Controller
public class WithdrawController {

  private BankAccountAccessAuthorizationService bankAccountAccessAuthorizationService;
  private WebAppBankService bankService;

  private static final Logger logger = LoggerFactory.getLogger(WithdrawController.class);

  /**
   * Конструктор со всеми зависимостями.
   */
  public WithdrawController(
      BankAccountAccessAuthorizationService bankAccountAccessAuthorizationService,
      WebAppBankService bankService) {
    this.bankAccountAccessAuthorizationService = bankAccountAccessAuthorizationService;
    this.bankService = bankService;
  }

  /**
   * Обработчик инициации снятия денежных средств в веб-приложении.
   */
  @PostMapping("/account/{number}/withdraw")
  public String depositAccount(@PathVariable("number") String accountNumber,
                               @RequestParam("username") String username,
                               @RequestParam("amount") Double amount,
                               @RequestParam("currency") String currency,
                               RedirectAttributes redirectAttributes
  ) throws WebAppUserNotFound,
      UnathorizedAccessToBankAccount, BankAccountNotExists {
    logger.info("Пользователь {} снимает {} {} со счета {}", username,
        amount, currency, accountNumber);

    BankAccountDto account =
        bankAccountAccessAuthorizationService.authorize(username, accountNumber);
    MonetaryAmount monetaryAmount = new MonetaryAmount(amount, currency);
    try {
      bankService.withdraw(account, monetaryAmount);
    } catch (InsufficientFundsException e) {
      logger.warn("Недостаточно средств для операции: пользователь {}, счет {}, снятие {} {}",
          username, accountNumber, amount, currency);
      redirectAttributes.addFlashAttribute("message", "webapp.error.withdraw.notsufficientfunds");
    } catch (UnknownCurrencyRate e) {
      logger.warn("Неизвестная валюта {}", currency);
      redirectAttributes.addFlashAttribute("message", "webapp.error.unknowncurrency");
    }

    return "redirect:/user/" + username + "/account/" + accountNumber;
  }
}
