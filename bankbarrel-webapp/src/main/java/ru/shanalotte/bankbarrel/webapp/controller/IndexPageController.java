package ru.shanalotte.bankbarrel.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.shanalotte.bankbarrel.webapp.dto.bankclient.BankClientInfoDto;

/**
 * Controller for the main page.
 */
@Controller
public class IndexPageController {

  private static final Logger logger = LoggerFactory.getLogger(IndexPageController.class);

  /**
   * Обработчик входа на главную страницу сайта.
   */
  @GetMapping("/")
  public String mainPage(Model model) {
    logger.info("Кто-то вошел на главную страницу сайта");
    model.addAttribute("dto", new BankClientInfoDto());
    return "index";
  }
}
