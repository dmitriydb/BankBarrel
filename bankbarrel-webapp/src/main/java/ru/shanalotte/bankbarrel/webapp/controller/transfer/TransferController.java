package ru.shanalotte.bankbarrel.webapp.controller.transfer;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;
import ru.shanalotte.bankbarrel.core.service.BankService;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankAccountDao;
import ru.shanalotte.bankbarrel.webapp.dto.transfer.TransferDto;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotExists;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotFound;
import ru.shanalotte.bankbarrel.webapp.exception.UnathorizedAccessToBankAccount;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.BankAccountAccessAuthorizationService;

/**
 * Контроллер для операций перевода.
 */
@Controller
public class TransferController {

  private BankAccountAccessAuthorizationService bankAccountAccessAuthorizationService;
  private BankAccountDao bankAccountDao;
  private BankService bankService;

  public TransferController(BankAccountAccessAuthorizationService bankAccountAccessAuthorizationService, BankAccountDao bankAccountDao, BankService bankService) {
    this.bankAccountAccessAuthorizationService = bankAccountAccessAuthorizationService;
    this.bankAccountDao = bankAccountDao;
    this.bankService = bankService;
  }

  @PostMapping("/transfer")
  public String processTransfer(RedirectAttributes redirectAttributes, Model model,
                                @Valid @ModelAttribute("transferDto") TransferDto dto,
                                BindingResult bindingResult,
                                @RequestParam("username") String username,
                                @RequestParam("accountFromNumber") String accountFromNumber)
      throws UnathorizedAccessToBankAccount, WebAppUserNotFound,
      BankAccountNotExists {
    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("message", "webapp.transfer.fillparameterscorrectly");
      return "redirect:/user/" + username + "/account/" + accountFromNumber;
    }
    try {
      BankAccount fromAccount = bankAccountAccessAuthorizationService.authorize(username,
          accountFromNumber);
      BankAccount toAccount = bankAccountDao.findByTransferDto(dto);
      MonetaryAmount monetaryAmount = new MonetaryAmount(dto.getAmount(), dto.getCurrency());
      bankService.transfer(fromAccount, toAccount, monetaryAmount);
      redirectAttributes.addFlashAttribute("successMessage", "webapp.transfer.success");
    } catch (InsufficientFundsException e) {
      redirectAttributes.addFlashAttribute("message", "webapp.error.withdraw.notsufficientfunds");
    } catch (UnknownCurrencyRate unknownCurrencyRate) {
      redirectAttributes.addFlashAttribute("message", "webapp.error.unknowncurrency");
    } catch (BankAccountNotFound BankAccountNotFound) {
      redirectAttributes.addFlashAttribute("message", "webapp.transfer.toaccountnotexists");
    }
    return "redirect:/user/" + username + "/account/" + accountFromNumber;
  }
}
