package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyTransferHistory;

public interface MoneyTransferHistoryDao extends JpaRepository<MoneyTransferHistory, Long> {
}
