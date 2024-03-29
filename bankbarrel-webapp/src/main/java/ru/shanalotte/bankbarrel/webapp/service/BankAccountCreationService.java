package ru.shanalotte.bankbarrel.webapp.service;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.config.FakeAccountNumberGenerator;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankAccountDao;
import ru.shanalotte.bankbarrel.webapp.dto.account.AccountOpeningDto;
import ru.shanalotte.bankbarrel.webapp.service.converter.AccountTypesNameConverter;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceRegistryProxy;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceUrlBuilder;

/**
 * Сервис, который открывает новые банковские счета.
 */
@Service
public class BankAccountCreationService {

  private AccountTypesNameConverter accountTypesNameConverter;
  private BankAccountDao bankAccountDao;
  private FakeAccountNumberGenerator fakeAccountNumberGenerator;
  private ServiceUrlBuilder serviceUrlBuilder;
  private ServiceRegistryProxy serviceRegistryProxy;

  /**
   * Конструктор со всеми зависимостями.
   */
  public BankAccountCreationService(AccountTypesNameConverter accountTypesNameConverter,
                                    BankAccountDao bankAccountDao,
                                    FakeAccountNumberGenerator fakeAccountNumberGenerator,
                                    ServiceUrlBuilder serviceUrlBuilder,
                                    ServiceRegistryProxy serviceRegistryProxy) {
    this.accountTypesNameConverter = accountTypesNameConverter;
    this.bankAccountDao = bankAccountDao;
    this.fakeAccountNumberGenerator = fakeAccountNumberGenerator;
    this.serviceUrlBuilder = serviceUrlBuilder;
    this.serviceRegistryProxy = serviceRegistryProxy;
  }

  /**
   * Открывает счет с информацией из дто и привязывает его клиенту.
   */
  public void createAccount(AccountOpeningDto dto, BankClientDto bankClient) {
    bankAccountDao.createAccount(dto, bankClient);
  }
}
