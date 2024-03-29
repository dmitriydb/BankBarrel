package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shanalotte.bankbarrel.appserver.domain.AccountOpening;

@SuppressWarnings("checkstyle:MissingJavadocType")
public interface AccountOpeningRepository extends JpaRepository<AccountOpening, Long> {
}
