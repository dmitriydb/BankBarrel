package ru.shanalotte.bankbarrel.webapp.dao.impl;

import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankClientDao;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.IServiceUrlBuilder;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceRegistryProxy;

@Repository
@Primary
public class RealClientDao implements BankClientDao {

  private IServiceUrlBuilder serviceUrlBuilder;
  private ServiceRegistryProxy serviceRegistryProxy;


  @Override
  public int count() {
    return 0;
  }

  @Override
  public void save(BankClientDto newBankClient) {

  }

  @Override
  public BankClientDto findByGivenName(String givenName) {
    return null;
  }
}
