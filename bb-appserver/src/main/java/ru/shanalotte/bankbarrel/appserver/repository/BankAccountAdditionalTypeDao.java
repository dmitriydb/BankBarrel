package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.appserver.domain.BankAccountAdditionalTypeEntity;
import ru.shanalotte.bankbarrel.appserver.domain.BankAccountTypeEntity;

@Repository
public interface BankAccountAdditionalTypeDao extends JpaRepository<BankAccountAdditionalTypeEntity, Long> {
  BankAccountAdditionalTypeEntity findByCode(String code);
}
