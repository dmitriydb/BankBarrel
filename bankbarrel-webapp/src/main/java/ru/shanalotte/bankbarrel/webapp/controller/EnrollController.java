package ru.shanalotte.bankbarrel.webapp.controller;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.shanalotte.bankbarrel.core.BankClient;
import ru.shanalotte.bankbarrel.webapp.dao.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.BankClientInfoDto;
import ru.shanalotte.bankbarrel.webapp.service.BankClientsEnrollingService;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

@Controller
public class EnrollController {

  private WebAppUserDao webAppUserDao;
  private BankClientsEnrollingService bankClientsEnrollingService;

  public EnrollController(WebAppUserDao webAppUserDao, BankClientsEnrollingService bankClientsEnrollingService) {
    this.webAppUserDao = webAppUserDao;
    this.bankClientsEnrollingService = bankClientsEnrollingService;
  }

  @PostMapping("/enroll")
  public String processEnroll(RedirectAttributes redirectAttributes, @RequestParam("username") String username, @Valid @ModelAttribute("dto") BankClientInfoDto dto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "index";
    }



    if (!webAppUserDao.isUserExists(username)) {
      BankClient bankClient = bankClientsEnrollingService.enrollClient(dto);
      webAppUserDao.addUser(new WebAppUser(username, bankClient));
    } else {
      redirectAttributes.addFlashAttribute("dto", dto);
      redirectAttributes.addFlashAttribute("message", "Username " + username + " is already exists!");
      return "redirect:/";
    }
    return "redirect:/user/" + username;
  }
}
