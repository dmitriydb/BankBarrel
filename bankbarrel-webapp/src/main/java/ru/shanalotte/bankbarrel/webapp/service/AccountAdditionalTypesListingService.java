package ru.shanalotte.bankbarrel.webapp.service;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.BankAccountAdditionalType;
import ru.shanalotte.bankbarrel.webapp.dto.ListingDtoItem;
import ru.shanalotte.bankbarrel.webapp.dto.ListingDto;

/**
 * Сервис возвращает ListingDto со списком всех дополнительных типов банковских счетов 2 уровня.
 */
@Service
public class AccountAdditionalTypesListingService implements ListingService {

  @Override
  public ListingDto getListingDto() {
    ListingDto dto = new ListingDto();
    for (BankAccountAdditionalType accountType : BankAccountAdditionalType.values()) {
      String code = accountType.name();
      String value = code.substring(0, 1).toUpperCase() + code.substring(1).toLowerCase();
      dto.addItem(new ListingDtoItem(code, value));
    }
    return dto;
  }
}
