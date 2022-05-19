package ru.shanalotte.bankbarrel.webapp.controller.transfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankAccountDao;
import ru.shanalotte.bankbarrel.webapp.dto.transfer.TransferDto;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotExists;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotFound;
import ru.shanalotte.bankbarrel.webapp.exception.UnathorizedAccessToBankAccount;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.BankAccountAccessAuthorizationService;
import ru.shanalotte.bankbarrel.webapp.service.WebAppBankService;

/**
 * Контроллер для операций перевода.
 */
@Controller
public class TransferController {

  private BankAccountAccessAuthorizationService bankAccountAccessAuthorizationService;
  private BankAccountDao bankAccountDao;
  private WebAppBankService bankService;

  private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

  /**
   * Конструктор со всеми зависимостями.
   */
  public TransferController(BankAccountAccessAuthorizationService
                                bankAccountAccessAuthorizationService,
                            BankAccountDao bankAccountDao, WebAppBankService bankService) {
    this.bankAccountAccessAuthorizationService = bankAccountAccessAuthorizationService;
    this.bankAccountDao = bankAccountDao;
    this.bankService = bankService;
  }

  /**
   * Контроллер операций денежных переводов в веб-приложении.
   */
  @PostMapping("/transfer")
  public String processTransfer(RedirectAttributes redirectAttributes, Model model,
                                @Valid @ModelAttribute("transferDto") TransferDto dto,
                                BindingResult bindingResult,
                                @RequestParam("username") String username,
                                @RequestParam("accountFromNumber") String accountFromNumber)
      throws UnathorizedAccessToBankAccount, WebAppUserNotFound,
      BankAccountNotExists, JsonProcessingException {
    logger.info("Пользователь {} пытается перевести {} {} со счета {} на счет {}",
        username, dto.getAmount(), dto.getCurrency(),
        accountFromNumber, dto.getAccountNumber());
    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("message", "webapp.transfer.fillparameterscorrectly");
      logger.warn("Ошибка в DTO перевода {}", new ObjectMapper().writeValueAsString(dto));
      return "redirect:/user/" + username + "/account/" + accountFromNumber;
    }
    try {
      BankAccountDto fromAccount = bankAccountAccessAuthorizationService.authorize(username,
          accountFromNumber);
      BankAccountDto toAccount = bankAccountDao.findByTransferDto(dto);
      MonetaryAmount monetaryAmount = new MonetaryAmount(dto.getAmount(), dto.getCurrency());
      bankService.transfer(fromAccount, toAccount, monetaryAmount);
      redirectAttributes.addFlashAttribute("successMessage", "webapp.transfer.success");
    } catch (BankAccountNotFound e) {
      logger.warn("Счет с номером {} или {} не найдены", accountFromNumber, dto.getAccountNumber());
      redirectAttributes.addFlashAttribute("message", "webapp.transfer.toaccountnotexists");
    } catch (UnknownCurrencyRate unknownCurrencyRate) {
      logger.warn("Валюта {} не существует", dto.getCurrency());
      redirectAttributes.addFlashAttribute("message", "webapp.error.unknowncurrency");
    } catch (InsufficientFundsException e) {
      logger.warn("На счете {} недостаточно средств для перевода {} {}", accountFromNumber,
          dto.getAmount(), dto.getCurrency());
      redirectAttributes.addFlashAttribute("message", "webapp.error.withdraw.notsufficientfunds");
    }
    return "redirect:/user/" + username + "/account/" + accountFromNumber;
  }
}
