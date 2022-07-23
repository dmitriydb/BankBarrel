package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyDepositStatus;

public interface MoneyDepositStatusDao extends JpaRepository<MoneyDepositStatus, Long> {
}
