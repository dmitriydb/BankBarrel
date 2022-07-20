package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shanalotte.bankbarrel.appserver.domain.AccountOpeningStatus;

@SuppressWarnings("checkstyle:MissingJavadocType")
public interface AccountOpeningStatusDao extends JpaRepository<AccountOpeningStatus, Long> {
}
