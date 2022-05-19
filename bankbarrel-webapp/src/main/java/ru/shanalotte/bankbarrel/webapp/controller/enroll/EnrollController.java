package ru.shanalotte.bankbarrel.webapp.controller.enroll;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Locale;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.bankclient.BankClientInfoDto;
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

  private static final Logger logger = LoggerFactory.getLogger(EnrollController.class);

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
   * @param dto дто с информацией о клиенте
   */
  @PostMapping("/enroll")
  public String processEnroll(RedirectAttributes redirectAttributes,
                              @Valid @ModelAttribute("dto") BankClientInfoDto dto,
                              BindingResult bindingResult) throws JsonProcessingException {
    logger.info("Попытка регистрации клиента {}", new ObjectMapper().writeValueAsString(dto));
    if (StringUtils.isBlank(dto.getEmail()) && StringUtils.isBlank(dto.getTelephone())) {
      String error = messageSource.getMessage(
          "webapp.validation.error.bothemailandtelephonemissing", null, Locale.ENGLISH);
      bindingResult.addError(new FieldError("dto", "telephone", error));
    }
    if (bindingResult.hasErrors()) {
      logger.warn("Некорректные данные для регистрации {}",
          new ObjectMapper().writeValueAsString(dto));
      return "index";
    }
    if (!webAppUserDao.isUserExists(dto.getUsername())) {
      BankClientDto bankClient = bankClientsEnrollingService.enrollClient(dto);
      webAppUserDao.addUser(new WebAppUser(dto.getUsername(), bankClient));
    } else {
      redirectAttributes.addFlashAttribute("dto", dto);
      redirectAttributes.addFlashAttribute("message",
          "Username " + dto.getUsername() + " is already exists!");
      logger.warn("Пользователь с именем {} уже существует", dto.getUsername());
      return "redirect:/";
    }
    return "redirect:/user/" + dto.getUsername();
  }
}
