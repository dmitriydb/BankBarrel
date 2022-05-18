package ru.shanalotte.bankbarrel.webapp.service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.config.FakeAccountNumberGenerator;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankAccountDao;
import ru.shanalotte.bankbarrel.webapp.dto.account.AccountOpeningDto;
import ru.shanalotte.bankbarrel.webapp.service.converter.AccountTypesNameConverter;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.IServiceRegistryProxy;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.IServiceUrlBuilder;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceRegistryProxy;

/**
 * Сервис, который открывает новые банковские счета.
 */
@Service
public class BankAccountCreationService {

  private AccountTypesNameConverter accountTypesNameConverter;
  private BankAccountDao bankAccountDao;
  private FakeAccountNumberGenerator fakeAccountNumberGenerator;
  private IServiceUrlBuilder serviceUrlBuilder;
  private IServiceRegistryProxy serviceRegistryProxy;

  public BankAccountCreationService(AccountTypesNameConverter accountTypesNameConverter, BankAccountDao bankAccountDao, FakeAccountNumberGenerator fakeAccountNumberGenerator, IServiceUrlBuilder serviceUrlBuilder, IServiceRegistryProxy serviceRegistryProxy) {
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
