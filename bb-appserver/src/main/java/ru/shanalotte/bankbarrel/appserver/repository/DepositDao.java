package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyDeposit;

/**
 * DAO для денежных вкладов.
 */
public interface DepositDao extends JpaRepository<MoneyDeposit, Long> {
}
