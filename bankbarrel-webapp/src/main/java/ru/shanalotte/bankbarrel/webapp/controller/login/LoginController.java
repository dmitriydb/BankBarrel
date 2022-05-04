package ru.shanalotte.bankbarrel.webapp.controller.login;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;

/**
 * Controller for the login process.
 */
@Controller
public class LoginController {

  private WebAppUserDao webAppUserDao;

  public LoginController(WebAppUserDao webAppUserDao) {
    this.webAppUserDao = webAppUserDao;
  }

  /**
   * Обрабатывает запрос на логин.
   * В данный момент пароль вводить не нужно, поэтому принимает только один параметр -
   * логин пользователя веб-приложения.
   *
   * @throws WebAppUserNotFound кидает, если такого пользователя не существует. (HTTP 401)
   */
  @PostMapping("/login")
  public String processLogin(RedirectAttributes redirectAttributes, @RequestParam("username") String username) throws WebAppUserNotFound {
    if (StringUtils.isBlank(username)) {
      redirectAttributes.addFlashAttribute("message", "webapp.login.fillusername");
      return "redirect:/";
    }
    if (!webAppUserDao.isUserExists(username)) {
      throw new WebAppUserNotFound(username);
    }
    return "redirect:/user/" + username;
  }
}
