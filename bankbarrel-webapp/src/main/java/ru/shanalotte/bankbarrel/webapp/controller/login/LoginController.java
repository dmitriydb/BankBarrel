package ru.shanalotte.bankbarrel.webapp.controller.login;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.shanalotte.bankbarrel.webapp.dao.impl.operations.WebAppOperationDao;
import ru.shanalotte.bankbarrel.webapp.dao.impl.operations.WebAppOperationHistoryDao;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperation;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperationHistory;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.monitoring.activity.UserActivityMonitoringService;

/**
 * Controller for the login process.
 */
@Controller
public class LoginController {

  private WebAppUserDao webAppUserDao;
  private WebAppOperationDao webAppOperationDao;
  private WebAppOperationHistoryDao webAppOperationHistoryDao;
  private UserActivityMonitoringService userActivityMonitoringService;

  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

  public LoginController(WebAppUserDao webAppUserDao,
                         WebAppOperationDao webAppOperationDao,
                         WebAppOperationHistoryDao webAppOperationHistoryDao,
                         UserActivityMonitoringService userActivityMonitoringService) {
    this.webAppUserDao = webAppUserDao;
    this.webAppOperationDao = webAppOperationDao;
    this.webAppOperationHistoryDao = webAppOperationHistoryDao;
    this.userActivityMonitoringService = userActivityMonitoringService;
  }

  /**
   * Обрабатывает запрос на логин.
   * В данный момент пароль вводить не нужно, поэтому принимает только один параметр -
   * логин пользователя веб-приложения.
   *
   * @throws WebAppUserNotFound кидает, если такого пользователя не существует. (HTTP 401)
   */
  @PostMapping("/login")
  public String processLogin(RedirectAttributes redirectAttributes,
                             @RequestParam("username") String username) throws WebAppUserNotFound {
    userActivityMonitoringService.auditLogin(username);
    WebAppOperation webAppOperation = new WebAppOperation();
    webAppOperation.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
    webAppOperation.setJson(username);
    webAppOperation.setType("LOGIN");
    webAppOperation.setStatus("LOGIN_PENDING");
    webAppOperationDao.createOperation(webAppOperation);
    Long operationId = webAppOperationDao.getId(webAppOperation);
    WebAppOperationHistory entry = new WebAppOperationHistory();
    entry.setOperationId(operationId);
    entry.setStartTs(LocalDateTime.now());
    entry.setStatus("LOGIN_CHECKING_DATA");
    webAppOperationHistoryDao.createEntry(entry);
    Long entryId = webAppOperationHistoryDao.getId(entry);
    webAppOperationDao.updateOperationStatus(operationId, "LOGIN_CHECKING_DATA");
    logger.info("Пользователь пытается войти под именем {}", username);
    if (StringUtils.isBlank(username)) {
      webAppOperationHistoryDao.closeEntry(entryId);
      entry.setStartTs(LocalDateTime.now());
      entry.setStatus("LOGIN_WRONG_DATA");
      webAppOperationHistoryDao.createAndCloseEntry(entry);
      webAppOperationDao.updateOperationStatus(operationId, "LOGIN_WRONG_DATA");
      webAppOperationDao.finishOperation(operationId);
      redirectAttributes.addFlashAttribute("message", "webapp.login.fillusername");
      return "redirect:/";
    }
    if (!webAppUserDao.isUserExists(username)) {
      webAppOperationHistoryDao.closeEntry(entryId);
      entry.setStartTs(LocalDateTime.now());
      entry.setStatus("LOGIN_USERNAME_NOT_EXISTS");
      webAppOperationHistoryDao.createAndCloseEntry(entry);
      webAppOperationDao.updateOperationStatus(operationId, "LOGIN_USERNAME_NOT_EXISTS");
      webAppOperationDao.finishOperation(operationId);
      throw new WebAppUserNotFound(username);
    }
    webAppOperationHistoryDao.closeEntry(entryId);
    entry.setStartTs(LocalDateTime.now());
    entry.setStatus("LOGIN_SUCCESS");
    webAppOperationHistoryDao.createAndCloseEntry(entry);
    webAppOperationDao.updateOperationStatus(operationId, "LOGIN_SUCCESS");
    webAppOperationDao.finishOperation(operationId);
    logger.warn("Пользователь {} вошел на сайт", username);
    return "redirect:/user/" + username;
  }
}
