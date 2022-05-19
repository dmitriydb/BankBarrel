package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shanalotte.bankbarrel.appserver.domain.OperationSource;

/**
 * DAO для OperationSource.
 */
public interface OperationSourceDao extends JpaRepository<OperationSource, Long> {
  OperationSource findByName(String name);
}
