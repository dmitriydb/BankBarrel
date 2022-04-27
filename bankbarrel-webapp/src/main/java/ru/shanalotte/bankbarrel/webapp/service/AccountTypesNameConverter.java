package ru.shanalotte.bankbarrel.webapp.service;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.BankAccountAdditionalType;
import ru.shanalotte.bankbarrel.core.domain.BankAccountType;

/**
 * Класс, который преобразует текстовое представление кода типа счёта
 * в эксземпляр соответствующего enum-а.
 */
@Service
public class AccountTypesNameConverter {

  public BankAccountType getAccountType(String type) {
    return BankAccountType.valueOf(type);
  }

  public BankAccountAdditionalType getAccountAdditionalType(String type) {
    return BankAccountAdditionalType.valueOf(type);
  }
}
