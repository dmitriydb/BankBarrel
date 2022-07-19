package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyTransferStatus;

public interface MoneyTransferStatusDao extends JpaRepository<MoneyTransferStatus, Long> {
}
