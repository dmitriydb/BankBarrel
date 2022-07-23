package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shanalotte.bankbarrel.core.domain.BankClient;

/**
 * DAO Для BankClient.
 */
public interface BankClientDao extends JpaRepository<BankClient, Long> {
}
