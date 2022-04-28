package ru.shanalotte.bankbarrel.webapp.service.listing;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.BankAccountAdditionalType;
import ru.shanalotte.bankbarrel.core.dto.ListingDto;
import ru.shanalotte.bankbarrel.core.dto.ListingDtoItem;
import ru.shanalotte.bankbarrel.core.service.EnumToListingDtoItemConverter;

/**
 * Сервис возвращает ListingDto со списком всех дополнительных типов банковских счетов 2 уровня.
 */
@Service
public class AccountAdditionalTypesListingService implements ListingService {

  private EnumToListingDtoItemConverter enumToListingDtoItemConverter;

  public AccountAdditionalTypesListingService(EnumToListingDtoItemConverter
                                                  enumToListingDtoItemConverter) {
    this.enumToListingDtoItemConverter = enumToListingDtoItemConverter;
  }

  @Override
  public ListingDto getListingDto() {
    ListingDto dto = new ListingDto();
    for (BankAccountAdditionalType accountType : BankAccountAdditionalType.values()) {
      ListingDtoItem item = enumToListingDtoItemConverter.convert(accountType.name());
      dto.addItem(item);
    }
    return dto;
  }
}
