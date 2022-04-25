package ru.shanalotte.bankbarrel.webapp.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shanalotte.bankbarrel.webapp.dto.AccountOpeningDto;

/**
 * Контроллер, обрабатывающий процесс создания банковского счёта.
 */
@Controller
public class AccountCreationController {

  @PostMapping("/account/create")
  public String openAccount(Model model, @RequestParam("username") String username,
                            @Valid @ModelAttribute("accountOpeningDto") AccountOpeningDto dto,
                            BindingResult bindingResult) {
    System.out.println(dto);
    return "redirect:/user/" + username;
  }

}
