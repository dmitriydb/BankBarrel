package ru.shanalotte.bankbarrel.webapp.config;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.webapp.dao.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.BankClientInfoDto;
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

  public DummyUserCreationConfig(BankClientsEnrollingService bankClientsEnrollingService,
                                 WebAppUserDao webAppUserDao) {
    this.bankClientsEnrollingService = bankClientsEnrollingService;
    this.webAppUserDao = webAppUserDao;
  }

  /**
   * Создает пользователя веб-приложения и привязывает к нему банковского клиента.
   */
  @Scheduled(initialDelay = 500, fixedDelay = Integer.MAX_VALUE)
  public void createDummyUser() {
    BankClientInfoDto dto = new BankClientInfoDto();
    dto.setEmail("admin@admin.ru");
    dto.setFirstName("Admin");
    dto.setLastName("Admin");
    dto.setTelephone("+7 333 333 33 33");
    BankClient bankClient = bankClientsEnrollingService.enrollClient(dto);
    webAppUserDao.addUser(new WebAppUser("admin", bankClient));
  }

}
