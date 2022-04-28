package ru.shanalotte.bankbarrel.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.shanalotte.bankbarrel.webapp.dto.bankclient.BankClientInfoDto;

/**
 * Controller for the main page.
 */
@Controller
public class IndexPageController {

  @GetMapping("/")
  public String mainPage(Model model) {
    model.addAttribute("dto", new BankClientInfoDto());
    return "index";
  }
}
