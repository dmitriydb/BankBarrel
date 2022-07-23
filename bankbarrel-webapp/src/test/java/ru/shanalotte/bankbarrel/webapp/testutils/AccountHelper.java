package ru.shanalotte.bankbarrel.webapp.testutils;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.account.AccountOpeningDto;
import ru.shanalotte.bankbarrel.webapp.service.BankAccountCreationService;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

@Service
public class AccountHelper {

  private WebAppUserDao webAppUserDao;
  private BankAccountCreationService bankAccountCreationService;

  public AccountHelper(WebAppUserDao webAppUserDao, BankAccountCreationService bankAccountCreationService) {
    this.webAppUserDao = webAppUserDao;
    this.bankAccountCreationService = bankAccountCreationService;
  }

  public void openThreeAccountsForUser(String username) {
    WebAppUser webAppUser = webAppUserDao.findByUsername(username);
    BankClientDto bankClient = webAppUser.getClient();
    AccountOpeningDto accountOpeningDto = TestDtoFactory.accountOpeningDto();
    bankAccountCreationService.createAccount(accountOpeningDto, bankClient);
    bankAccountCreationService.createAccount(accountOpeningDto, bankClient);
    bankAccountCreationService.createAccount(accountOpeningDto, bankClient);
  }
}
