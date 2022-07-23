package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;

/**
 * DAO для BankAccount.
 */
public interface BankAccountDao extends JpaRepository<BankAccount, String> {
  BankAccount findByNumber(String number);
}
