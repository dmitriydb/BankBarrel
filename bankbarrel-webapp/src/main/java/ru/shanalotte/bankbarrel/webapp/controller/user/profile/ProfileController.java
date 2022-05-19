package ru.shanalotte.bankbarrel.webapp.controller.user.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankClientDao;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.profile.ClientProfileDto;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

/**
 * Контроллер для действий, связанных с профилем пользователя.
 */
@Controller
public class ProfileController {

  private WebAppUserDao webAppUserDao;
  private BankClientDao bankClientDao;

  private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

  public ProfileController(WebAppUserDao webAppUserDao, BankClientDao bankClientDao) {
    this.webAppUserDao = webAppUserDao;
    this.bankClientDao = bankClientDao;
  }

  /**
   * Обрабатывает открытие профиля в веб-приложении.
   *
   * @throws WebAppUserNotFound если такой пользователь не существует
   */
  @GetMapping("/user/{username}/profile")
  public String showProfile(Model model, @PathVariable("username") String username)
      throws WebAppUserNotFound {
    logger.info("Пользователь {} заходит в свой профиль", username);
    if (!webAppUserDao.isUserExists(username)) {
      logger.warn("Пользователь {} не существует", username);
      throw new WebAppUserNotFound(username);
    }
    WebAppUser webAppUser = webAppUserDao.findByUsername(username);
    BankClientDto bankClient = webAppUser.getClient();
    ClientProfileDto dto = ClientProfileDto.of(bankClient);
    dto.setUsername(username);
    model.addAttribute("username", username);
    model.addAttribute("profileDto", dto);
    return "profile";
  }
}
