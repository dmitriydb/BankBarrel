package ru.shanalotte.bankbarrel.webapp.service.listing;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.BankAccountAdditionalType;
import ru.shanalotte.bankbarrel.core.dto.CodeAndValuesPairsListWrapper;
import ru.shanalotte.bankbarrel.core.dto.CodeAndValuePair;
import ru.shanalotte.bankbarrel.core.service.EnumToCodeAndValuePairConverter;

/**
 * Сервис возвращает CodeAndValuesPairsListWrapper со списком всех дополнительных типов банковских счетов 2 уровня.
 */
@Service
public class AccountAdditionalTypesListingService implements ListingService {

  private EnumToCodeAndValuePairConverter enumToCodeAndValuePairConverter;

  public AccountAdditionalTypesListingService(EnumToCodeAndValuePairConverter
                                                  enumToCodeAndValuePairConverter) {
    this.enumToCodeAndValuePairConverter = enumToCodeAndValuePairConverter;
  }

  @Override
  public CodeAndValuesPairsListWrapper getListingDto() {
    CodeAndValuesPairsListWrapper dto = new CodeAndValuesPairsListWrapper();
    for (BankAccountAdditionalType accountType : BankAccountAdditionalType.values()) {
      CodeAndValuePair item = enumToCodeAndValuePairConverter.convert(accountType.name());
      dto.addItem(item);
    }
    return dto;
  }
}
