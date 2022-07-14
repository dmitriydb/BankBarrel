package ru.shanalotte.bankbarrel.webapp.service.listing;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.BankAccountType;
import ru.shanalotte.bankbarrel.core.dto.CodeAndValuesPairsListWrapper;
import ru.shanalotte.bankbarrel.core.dto.CodeAndValuePair;
import ru.shanalotte.bankbarrel.core.service.EnumToCodeAndValuePairConverter;

/**
 * Сервис, который возвращает DTO со всеми возможными типами банковского счета 1 уровня.
 * Checking, Saving и т.д.
 */
@Service
public class AccountTypeListingService implements ListingService {

  private EnumToCodeAndValuePairConverter enumToCodeAndValuePairConverter;

  public AccountTypeListingService(EnumToCodeAndValuePairConverter enumToCodeAndValuePairConverter) {
    this.enumToCodeAndValuePairConverter = enumToCodeAndValuePairConverter;
  }

  @Override
  public CodeAndValuesPairsListWrapper getListingDto() {
    CodeAndValuesPairsListWrapper dto = new CodeAndValuesPairsListWrapper();
    for (BankAccountType accountType : BankAccountType.values()) {
      CodeAndValuePair item = enumToCodeAndValuePairConverter.convert(accountType.name());
      dto.addItem(item);
    }
    return dto;
  }

}
