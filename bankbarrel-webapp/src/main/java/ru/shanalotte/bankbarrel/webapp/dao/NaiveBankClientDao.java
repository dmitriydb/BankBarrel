package ru.shanalotte.bankbarrel.webapp.dao;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.core.domain.BankClient;

/**
 * Заглушка для DAO клиентов банка. Хранит всё в памяти.
 */
@Repository
public class NaiveBankClientDao implements BankClientDao {

  private Set<BankClient> bankClients = new HashSet<>();

  @Override
  public int count() {
    return bankClients.size();
  }

  @Override
  public void save(BankClient newBankClient) {
    bankClients.add(newBankClient);
  }

  @Override
  public BankClient findByGivenName(String givenName) {
    return bankClients.stream().filter(client -> client.getGivenName().equals(givenName))
        .findFirst()
        .orElse(null);
  }
}
