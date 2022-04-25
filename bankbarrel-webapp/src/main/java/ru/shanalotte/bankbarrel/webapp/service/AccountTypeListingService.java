package ru.shanalotte.bankbarrel.webapp.service;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.BankAccountType;
import ru.shanalotte.bankbarrel.webapp.dto.ListingDtoItem;
import ru.shanalotte.bankbarrel.webapp.dto.ListingDto;

/**
 * Сервис, который возвращает DTO со всеми возможными типами банковского счета 1 уровня.
 * Checking, Saving и т.д.
 */
@Service
public class AccountTypeListingService implements ListingService {

  @Override
  public ListingDto getListingDto() {
    ListingDto dto = new ListingDto();
    for (BankAccountType accountType : BankAccountType.values()) {
      String code = accountType.name();
      String value = code.substring(0, 1).toUpperCase() + code.substring(1).toLowerCase();
      dto.addItem(new ListingDtoItem(code, value));
    }
    return dto;
  }

}
