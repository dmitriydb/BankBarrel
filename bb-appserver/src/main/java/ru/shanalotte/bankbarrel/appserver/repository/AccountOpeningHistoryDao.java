package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shanalotte.bankbarrel.appserver.domain.AccountOpeningHistory;

@SuppressWarnings("checkstyle:MissingJavadocType")
public interface AccountOpeningHistoryDao extends JpaRepository<AccountOpeningHistory, Long> {
}
