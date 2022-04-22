package ru.shanalotte.bankbarrel.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for the login process.
 */
@Controller
public class LoginController {

  @PostMapping("/login")
  public String processLogin(@RequestParam("username") String username) {
    return "redirect:/user/" + username;
  }
}
