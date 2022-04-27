package ru.shanalotte.bankbarrel.webapp.service;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.BankAccountType;
import ru.shanalotte.bankbarrel.core.dto.ListingDto;
import ru.shanalotte.bankbarrel.core.dto.ListingDtoItem;
import ru.shanalotte.bankbarrel.core.service.EnumToListingDtoItemConverter;

/**
 * Сервис, который возвращает DTO со всеми возможными типами банковского счета 1 уровня.
 * Checking, Saving и т.д.
 */
@Service
public class AccountTypeListingService implements ListingService {

  private EnumToListingDtoItemConverter enumToListingDtoItemConverter;

  public AccountTypeListingService(EnumToListingDtoItemConverter enumToListingDtoItemConverter) {
    this.enumToListingDtoItemConverter = enumToListingDtoItemConverter;
  }

  @Override
  public ListingDto getListingDto() {
    ListingDto dto = new ListingDto();
    for (BankAccountType accountType : BankAccountType.values()) {
      ListingDtoItem item = enumToListingDtoItemConverter.convert(accountType.name());
      dto.addItem(item);
    }
    return dto;
  }

}
