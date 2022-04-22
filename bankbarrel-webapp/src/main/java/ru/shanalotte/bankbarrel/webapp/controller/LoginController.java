package ru.shanalotte.bankbarrel.webapp.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shanalotte.bankbarrel.core.BankClient;
import ru.shanalotte.bankbarrel.webapp.dao.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.BankClientInfoDto;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.BankClientsEnrollingService;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

/**
 * Controller for the login process.
 */
@Controller
public class LoginController {

  private WebAppUserDao webAppUserDao;

  public LoginController(WebAppUserDao webAppUserDao) {
    this.webAppUserDao = webAppUserDao;
  }

  @PostMapping("/login")
  public String processLogin(@RequestParam("username") String username) throws WebAppUserNotFound {
    if (!webAppUserDao.isUserExists(username)) {
      throw new WebAppUserNotFound(username);
    }
    return "redirect:/user/" + username;
  }
}
