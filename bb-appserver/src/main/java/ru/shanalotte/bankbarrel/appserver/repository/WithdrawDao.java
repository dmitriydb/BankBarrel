package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyWithdraw;

/**
 * DAO для MoneyWithdraw.
 */
public interface WithdrawDao extends JpaRepository<MoneyWithdraw, Long> {

}
