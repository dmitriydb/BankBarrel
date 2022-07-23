package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyTransfer;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyWithdraw;

/**
 * DAO для класса Transfer.
 */
public interface TransferDao extends JpaRepository<MoneyTransfer, Long> {

}
