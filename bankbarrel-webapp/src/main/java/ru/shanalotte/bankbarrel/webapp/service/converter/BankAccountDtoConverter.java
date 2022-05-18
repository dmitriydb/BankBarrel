package ru.shanalotte.bankbarrel.webapp.service.converter;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.service.EnumToListingDtoItemConverter;
import ru.shanalotte.bankbarrel.webapp.dto.account.BankAccountWebAppDto;


@Service
public class BankAccountDtoConverter {

  private EnumToListingDtoItemConverter enumToListingDtoItemConverter;

  public BankAccountDtoConverter(EnumToListingDtoItemConverter enumToListingDtoItemConverter) {
    this.enumToListingDtoItemConverter = enumToListingDtoItemConverter;
  }

  public BankAccountWebAppDto convert(BankAccountDto account) {
    String type = enumToListingDtoItemConverter
        .convert(account.getType()).getValue();
    String additionalType = enumToListingDtoItemConverter
        .convert(account.getAdditionalType()).getValue();
    String currency = account.getCurrency();
    String number = account.getNumber();
    BankAccountWebAppDto dto = new BankAccountWebAppDto(number, type, additionalType, currency);
    return dto;
  }
}
