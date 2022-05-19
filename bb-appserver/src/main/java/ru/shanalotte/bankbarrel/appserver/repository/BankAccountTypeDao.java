package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.appserver.domain.BankAccountTypeEntity;
import ru.shanalotte.bankbarrel.core.domain.BankAccountType;

/**
 * DAO для BankAccountTypeEntity.
 */
@Repository
public interface BankAccountTypeDao extends JpaRepository<BankAccountTypeEntity, Long> {
  BankAccountTypeEntity findByCode(String code);
}
