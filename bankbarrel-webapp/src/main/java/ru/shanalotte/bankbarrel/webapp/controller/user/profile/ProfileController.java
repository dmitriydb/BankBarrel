package ru.shanalotte.bankbarrel.webapp.controller.user.profile;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
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
    if (!webAppUserDao.isUserExists(username)) {
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
