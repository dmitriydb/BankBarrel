package ru.shanalotte.bankbarrel.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shanalotte.bankbarrel.webapp.dao.WebAppUserDao;

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
  public String processLogin(@RequestParam("username") String username) {
    if (!webAppUserDao.isUserExists(username)) {
      webAppUserDao.addUser(username);
    }
    return "redirect:/user/" + username;
  }
}
