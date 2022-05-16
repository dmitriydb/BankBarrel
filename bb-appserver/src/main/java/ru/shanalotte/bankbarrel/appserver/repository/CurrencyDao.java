package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.appserver.domain.Currency;

@Repository
public interface CurrencyDao extends JpaRepository<Currency, Long> {
  Currency findByCode(String code);
}
