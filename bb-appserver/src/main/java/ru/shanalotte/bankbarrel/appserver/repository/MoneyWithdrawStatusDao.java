package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyWithdrawStatus;

public interface MoneyWithdrawStatusDao extends JpaRepository<MoneyWithdrawStatus, Long> {
}
