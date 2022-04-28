package ru.shanalotte.bankbarrel.webapp.controller.account;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.account.AccountOpeningDto;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.BankAccountCreationService;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

/**
 * Контроллер, обрабатывающий процесс создания банковского счёта.
 */
@Controller
public class AccountCreationController {

  private WebAppUserDao webAppUserDao;
  private BankAccountCreationService bankAccountCreationService;

  public AccountCreationController(WebAppUserDao webAppUserDao,
                                   BankAccountCreationService bankAccountCreationService) {
    this.webAppUserDao = webAppUserDao;
    this.bankAccountCreationService = bankAccountCreationService;
  }

  /**
   * Создает новый банковский счет для пользователя веб-приложения.
   *
   * @throws WebAppUserNotFound если был совершен запрос для несуществующего пользователя
   */
  @PostMapping("/account/create")
  public String openAccount(Model model, @RequestParam("username") String username,
                            @Valid @ModelAttribute("accountOpeningDto") AccountOpeningDto dto,
                            BindingResult bindingResult) throws WebAppUserNotFound {
    WebAppUser webAppUser = webAppUserDao.findByUsername(username);
    if (webAppUser == null) {
      throw new WebAppUserNotFound(username);
    }
    BankClient bankClient = webAppUser.getClient();
    bankAccountCreationService.createAccount(dto, bankClient);

    return "redirect:/user/" + username;
  }

}
