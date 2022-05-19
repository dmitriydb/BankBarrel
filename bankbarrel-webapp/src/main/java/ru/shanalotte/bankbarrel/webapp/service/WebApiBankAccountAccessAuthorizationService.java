package ru.shanalotte.bankbarrel.webapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankAccountDao;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankClientDao;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotExists;
import ru.shanalotte.bankbarrel.webapp.exception.UnathorizedAccessToBankAccount;
import ru.shanalotte.bankbarrel.webapp.exception.WebAppUserNotFound;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceRegistryProxy;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceUrlBuilder;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

/**
 * Сервис, который выдает разрешения клиентам банка на доступ к банковским счетам.
 */
@Service
@Profile({"dev", "production"})
public class WebApiBankAccountAccessAuthorizationService
    implements BankAccountAccessAuthorizationService {

  private WebAppUserDao webAppUserDao;
  private BankAccountDao bankAccountDao;
  private BankClientDao bankClientDao;
  private ServiceUrlBuilder serviceUrlBuilder;
  private ServiceRegistryProxy serviceRegistryProxy;

  /**
   * Конструктор со всеми зависимостями.
   */
  public WebApiBankAccountAccessAuthorizationService(WebAppUserDao webAppUserDao,
                                                     BankAccountDao bankAccountDao,
                                                     BankClientDao bankClientDao,
                                                     ServiceUrlBuilder serviceUrlBuilder,
                                                     ServiceRegistryProxy serviceRegistryProxy) {
    this.webAppUserDao = webAppUserDao;
    this.bankAccountDao = bankAccountDao;
    this.bankClientDao = bankClientDao;
    this.serviceUrlBuilder = serviceUrlBuilder;
    this.serviceRegistryProxy = serviceRegistryProxy;
  }

  /**
   * Определяет, есть ли у клиента банка счет с заданным номером.
   *
   * @param accountNumber искомый номер счета
   */
  public boolean bankClientHasTheAccountWithNumber(BankClientDto client, String accountNumber) {
    Long id = bankClientDao.idByDto(client);
    String url = serviceUrlBuilder.buildServiceUrl(
        serviceRegistryProxy.getWebApiInfo()) + "/clients/" + id + "/accounts";
    List<BankAccountDto> accountDtos = Arrays.stream(
            new RestTemplate().getForEntity(url, BankAccountDto[].class)
                .getBody())
        .collect(Collectors.toList());
    return accountDtos.stream().anyMatch(dto -> dto.getNumber().equals(accountNumber));
  }

  /**
   * Авторизует доступ пользователя веб-приложения к банковскому счету
   * и возвращает банковского клиента в успешном случае.
   *
   * @param username      логин пользователя веб-приложения
   * @param accountNumber номер банковского счета
   */
  public BankAccountDto authorize(String username, String accountNumber)
      throws WebAppUserNotFound, BankAccountNotExists, UnathorizedAccessToBankAccount {
    WebAppUser webAppUser = webAppUserDao.findByUsername(username);
    if (webAppUser == null) {
      throw new WebAppUserNotFound(username);
    }
    BankAccountDto bankAccount = bankAccountDao.findByNumber(accountNumber);
    if (bankAccount == null) {
      throw new BankAccountNotExists(accountNumber);
    }
    BankClientDto client = webAppUser.getClient();
    if (!bankClientHasTheAccountWithNumber(client, accountNumber)) {
      throw new UnathorizedAccessToBankAccount(client + " " + accountNumber);
    }
    return bankAccount;
  }
}
