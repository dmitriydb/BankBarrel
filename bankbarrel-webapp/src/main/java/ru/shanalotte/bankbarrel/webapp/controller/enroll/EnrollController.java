package ru.shanalotte.bankbarrel.webapp.controller.enroll;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
import javax.validation.Valid;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.dao.impl.operations.WebAppOperationDao;
import ru.shanalotte.bankbarrel.webapp.dao.impl.operations.WebAppOperationHistoryDao;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.bankclient.BankClientInfoDto;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperation;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperationHistory;
import ru.shanalotte.bankbarrel.webapp.service.BankClientsEnrollingService;
import ru.shanalotte.bankbarrel.webapp.service.monitoring.activity.UserActivityMonitoringService;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

/**
 * Контроллер, который обрабатывает запросы на регистрацию новых пользователей веб-приложения.
 */
@Controller
public class EnrollController {

  private WebAppUserDao webAppUserDao;
  private BankClientsEnrollingService bankClientsEnrollingService;
  private MessageSource messageSource;
  private WebAppOperationDao webAppOperationDao;
  private WebAppOperationHistoryDao webAppOperationHistoryDao;
  private UserActivityMonitoringService userActivityMonitoringService;

  private static final Logger logger = LoggerFactory.getLogger(EnrollController.class);

  /**
   * Конструктор со всеми зависимостями.
   */
  public EnrollController(WebAppUserDao webAppUserDao,
                          BankClientsEnrollingService bankClientsEnrollingService,
                          MessageSource messageSource,
                          WebAppOperationDao webAppOperationDao,
                          WebAppOperationHistoryDao webAppOperationHistoryDao,
                          UserActivityMonitoringService userActivityMonitoringService) {
    this.webAppUserDao = webAppUserDao;
    this.bankClientsEnrollingService = bankClientsEnrollingService;
    this.messageSource = messageSource;
    this.webAppOperationDao = webAppOperationDao;
    this.webAppOperationHistoryDao = webAppOperationHistoryDao;
    this.userActivityMonitoringService = userActivityMonitoringService;
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
    String json = new ObjectMapper().writeValueAsString(dto);
    userActivityMonitoringService.auditEnroll(json);
    logger.info("Попытка регистрации клиента {}", json);
    WebAppOperation webAppOperation = new WebAppOperation();
    webAppOperation.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
    webAppOperation.setJson(json);
    webAppOperation.setType("REGISTRATION");
    webAppOperation.setStatus("REGISTRATION_PENDING");
    webAppOperationDao.createOperation(webAppOperation);
    Long operationId = webAppOperationDao.getId(webAppOperation);
    WebAppOperationHistory entry = new WebAppOperationHistory();
    entry.setOperationId(operationId);
    entry.setStartTs(LocalDateTime.now());
    entry.setStatus("REGISTRATION_CHECKING_DATA");
    webAppOperationHistoryDao.createEntry(entry);
    Long entryId = webAppOperationHistoryDao.getId(entry);
    webAppOperationDao.updateOperationStatus(operationId, "REGISTRATION_CHECKING_DATA");
    if (StringUtils.isBlank(dto.getEmail()) && StringUtils.isBlank(dto.getTelephone())) {
      String error = messageSource.getMessage(
          "webapp.validation.error.bothemailandtelephonemissing", null, Locale.ENGLISH);
      bindingResult.addError(new FieldError("dto", "telephone", error));
    }
    if (bindingResult.hasErrors()) {
      logger.warn("Некорректные данные для регистрации {}",
          new ObjectMapper().writeValueAsString(dto));
      webAppOperationHistoryDao.closeEntry(entryId);
      webAppOperationDao.updateOperationStatus(operationId, "REGISTRATION_WRONG_DATA");
      webAppOperationDao.finishOperation(operationId);
      return "index";
    }
    webAppOperationHistoryDao.closeEntry(entryId);
    entry.setStartTs(LocalDateTime.now());
    entry.setStatus("REGISTRATION_PENDING");
    webAppOperationHistoryDao.createEntry(entry);
    entryId = webAppOperationHistoryDao.getId(entry);
    webAppOperationDao.updateOperationStatus(operationId, "REGISTRATION_PENDING");

    if (!webAppUserDao.isUserExists(dto.getUsername())) {
      BankClientDto bankClient = bankClientsEnrollingService.enrollClient(dto);
      webAppUserDao.addUser(new WebAppUser(dto.getUsername(), bankClient));
      webAppOperationHistoryDao.closeEntry(entryId);
      webAppOperationDao.updateOperationStatus(operationId, "REGISTRATION_SUCCESS");
      webAppOperationDao.finishOperation(operationId);
    } else {
      webAppOperationHistoryDao.closeEntry(entryId);
      webAppOperationDao.updateOperationStatus(operationId, "REGISTRATION_USER_ALREADY_EXISTS");
      webAppOperationDao.finishOperation(operationId);
      redirectAttributes.addFlashAttribute("dto", dto);
      redirectAttributes.addFlashAttribute("message",
          "webapp.error.usernamealreadyexists");
      logger.warn("Пользователь с именем {} уже существует", dto.getUsername());
      return "redirect:/";
    }
    return "redirect:/user/" + dto.getUsername();
  }
}
