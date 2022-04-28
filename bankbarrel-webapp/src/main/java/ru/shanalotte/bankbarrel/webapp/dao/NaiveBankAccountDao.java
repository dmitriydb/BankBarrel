package ru.shanalotte.bankbarrel.webapp.dao;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.BankClient;

/**
 * Заглушка для DAO клиентов банка. Хранит всё в памяти.
 */
@Repository
public class NaiveBankAccountDao implements BankAccountDao {

  private Set<BankAccount> bankAccounts = new HashSet<>();

  @Override
  public void save(BankAccount account) {
    bankAccounts.add(account);
  }

  @Override
  public BankAccount findByNumber(String number) {
    return bankAccounts.stream().filter(account -> account.getIdentifier().equals(number))
        .findFirst().orElse(null);
  }
}
