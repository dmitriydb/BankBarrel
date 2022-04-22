package ru.shanalotte.bankbarrel.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Controller for the user page.
 */
@Controller
public class UserPageController {

  @GetMapping("/user/{username}")
  public String userPage(@PathVariable("username") String username, Model model) {
    model.addAttribute("username", username);
    return "user-page";
  }
}
