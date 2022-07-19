package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyWithdrawHistory;

public interface MoneyWithdrawHistoryDao extends JpaRepository<MoneyWithdrawHistory, Long> {
}
