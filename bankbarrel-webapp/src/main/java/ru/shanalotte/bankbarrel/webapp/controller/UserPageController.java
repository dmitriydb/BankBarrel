package ru.shanalotte.bankbarrel.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.shanalotte.bankbarrel.webapp.dao.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.AccountOpeningDto;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;

/**
 * Controller for the user page.
 */
@Controller
public class UserPageController {

  @Autowired
  private WebAppUserDao webAppUserDao;

  @GetMapping("/user/{username}")
  public String userPage(@PathVariable("username") String username, Model model) throws WebAppUserNotFound {
    if (!webAppUserDao.isUserExists(username)) {
      throw new WebAppUserNotFound(username);
    }
    model.addAttribute("username", username);
    model.addAttribute("accountOpeningDto", new AccountOpeningDto());
    return "user-page";
  }
}
