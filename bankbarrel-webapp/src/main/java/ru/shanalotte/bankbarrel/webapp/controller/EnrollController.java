package ru.shanalotte.bankbarrel.webapp.controller;

import java.util.Locale;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.shanalotte.bankbarrel.core.BankClient;
import ru.shanalotte.bankbarrel.webapp.dao.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.BankClientInfoDto;
import ru.shanalotte.bankbarrel.webapp.service.BankClientsEnrollingService;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

/**
 * Контроллер, который обрабатывает запросы на регистрацию новых пользователей веб-приложения.
 */
@Controller
public class EnrollController {

  private WebAppUserDao webAppUserDao;
  private BankClientsEnrollingService bankClientsEnrollingService;
  private MessageSource messageSource;

  /**
   * Конструктор со всеми зависимостями.
   */
  public EnrollController(WebAppUserDao webAppUserDao,
                          BankClientsEnrollingService bankClientsEnrollingService,
                          MessageSource messageSource) {
    this.webAppUserDao = webAppUserDao;
    this.bankClientsEnrollingService = bankClientsEnrollingService;
    this.messageSource = messageSource;
  }

  /**
   * Обрабатывает запрос на регистрацию.
   *
   * @param redirectAttributes нужны для передачи DTO назад в модель
   *                           при редиректе на страницу регистрации.
   * @param username логин пользователя
   * @param dto дто с информацией о клиенте
   */
  @PostMapping("/enroll")
  public String processEnroll(RedirectAttributes redirectAttributes,
                              @RequestParam("username") String username,
                              @Valid @ModelAttribute("dto") BankClientInfoDto dto,
                              BindingResult bindingResult) {
    if (StringUtils.isBlank(dto.getEmail()) && StringUtils.isBlank(dto.getTelephone())) {
      String error = messageSource.getMessage(
          "webapp.validation.error.bothemailandtelephonemissing", null, Locale.ENGLISH);
      bindingResult.addError(new FieldError("dto", "telephone", error));
    }
    if (bindingResult.hasErrors()) {
      return "index";
    }
    if (!webAppUserDao.isUserExists(username)) {
      BankClient bankClient = bankClientsEnrollingService.enrollClient(dto);
      webAppUserDao.addUser(new WebAppUser(username, bankClient));
    } else {
      redirectAttributes.addFlashAttribute("dto", dto);
      redirectAttributes.addFlashAttribute("message",
          "Username " + username + " is already exists!");
      return "redirect:/";
    }
    return "redirect:/user/" + username;
  }
}
