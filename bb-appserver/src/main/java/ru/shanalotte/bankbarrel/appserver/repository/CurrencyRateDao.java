package ru.shanalotte.bankbarrel.appserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shanalotte.bankbarrel.core.domain.CurrencyRateRule;

public interface CurrencyRateDao extends JpaRepository<CurrencyRateRule, String> {
  CurrencyRateRule findByCurrency(String currency);
}
