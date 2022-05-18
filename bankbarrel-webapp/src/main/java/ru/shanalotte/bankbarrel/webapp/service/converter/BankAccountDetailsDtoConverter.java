package ru.shanalotte.bankbarrel.webapp.service.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.service.EnumToListingDtoItemConverter;
import ru.shanalotte.bankbarrel.webapp.dto.account.BankAccountDetailsDto;
import ru.shanalotte.bankbarrel.webapp.dto.account.BankAccountWebAppDto;

/**
 * Класс, который преобразует бизнес-объекты класса BankAccount в соответствующее DTO.
 *
 * @see BankAccountWebAppDto
 */
@Service
public class BankAccountDetailsDtoConverter {

  private EnumToListingDtoItemConverter enumToListingDtoItemConverter;
  private CurrencyPresentationConverter currencyPresentationConverter;

  public BankAccountDetailsDtoConverter(EnumToListingDtoItemConverter
                                            enumToListingDtoItemConverter,
                                        CurrencyPresentationConverter
                                            currencyPresentationConverter) {
    this.enumToListingDtoItemConverter = enumToListingDtoItemConverter;
    this.currencyPresentationConverter = currencyPresentationConverter;
  }

  /**
   * Метод преобразует бизнес-объект класса BankAccount в соответствующее DTO.
   * В данном методе названия констант перечислений банковских счетов напрямую передаются
   * в сервис enumToListingDtoItemConverter, что в дальнейшем может создать ненужный coupling.
   */
  public BankAccountDetailsDto convert(BankAccountDto account) {
    String type = enumToListingDtoItemConverter
        .convert(account.getType()).getValue();
    String additionalType = enumToListingDtoItemConverter
        .convert(account.getAdditionalType()).getValue();
    String currency = account.getCurrency();
    String currencySign = currencyPresentationConverter.currencyToSign(currency);
    String number = account.getNumber();
    String description = account.getDescription();
    BankAccountDetailsDto dto = new BankAccountDetailsDto();
    dto.setBalance(new BigDecimal(account.getBalance()).setScale(2, RoundingMode.HALF_UP).toString());
    dto.setAdditionalType(additionalType);
    dto.setCurrency(currency);
    dto.setDescription(description);
    dto.setNumber(number);
    dto.setType(type);
    dto.setCurrencySign(currencySign);
    return dto;
  }
}
