package ru.shanalotte.bankbarrel.webapp.config;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.account.AccountOpeningDto;
import ru.shanalotte.bankbarrel.webapp.dto.bankclient.BankClientInfoDto;
import ru.shanalotte.bankbarrel.webapp.service.BankAccountCreationService;
import ru.shanalotte.bankbarrel.webapp.service.BankClientsEnrollingService;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

/**
 * Класс, который создает тестового пользователя с логином admin на время разработки.
 */
@Service
@Profile("dev")
public class DummyUserCreationConfig {

  private BankClientsEnrollingService bankClientsEnrollingService;
  private WebAppUserDao webAppUserDao;
  private BankAccountCreationService bankAccountCreationService;

  public DummyUserCreationConfig(BankClientsEnrollingService bankClientsEnrollingService,
                                 WebAppUserDao webAppUserDao,
                                 BankAccountCreationService bankAccountCreationService) {
    this.bankClientsEnrollingService = bankClientsEnrollingService;
    this.webAppUserDao = webAppUserDao;
    this.bankAccountCreationService = bankAccountCreationService;
  }

  /**
   * Создает пользователя веб-приложения и привязывает к нему банковского клиента.
   */
  @Scheduled(initialDelay = 500, fixedDelay = Integer.MAX_VALUE)
  public void createDummyUserAndFakeAccounts() {
    BankClientInfoDto dto = new BankClientInfoDto();
    dto.setEmail("admin@admin.ru");
    dto.setFirstName("Admin");
    dto.setLastName("Admin");
    dto.setTelephone("+7 333 333 33 33");
    BankClient bankClient = bankClientsEnrollingService.enrollClient(dto);
    webAppUserDao.addUser(new WebAppUser("admin", bankClient));
    AccountOpeningDto accountOpeningDto = new AccountOpeningDto();
    accountOpeningDto.setCurrency("USD");
    accountOpeningDto.setAccountType("CHECKING");
    accountOpeningDto.setAccountAdditionalType("TRADITIONAL");
    bankAccountCreationService.createAccount(accountOpeningDto, bankClient);
    accountOpeningDto.setCurrency("RUB");
    bankAccountCreationService.createAccount(accountOpeningDto, bankClient);
    accountOpeningDto.setCurrency("KZT");
    bankAccountCreationService.createAccount(accountOpeningDto, bankClient);
  }



}
