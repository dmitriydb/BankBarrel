package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shanalotte.bankbarrel.appserver.domain.AccountOpeningHistory;

public interface AccountOpeningHistoryDao extends JpaRepository<AccountOpeningHistory, Long> {
}
