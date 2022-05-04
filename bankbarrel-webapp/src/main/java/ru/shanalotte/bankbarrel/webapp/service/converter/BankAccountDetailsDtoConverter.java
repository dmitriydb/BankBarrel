package ru.shanalotte.bankbarrel.webapp.service.converter;

import java.math.RoundingMode;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.service.EnumToListingDtoItemConverter;
import ru.shanalotte.bankbarrel.webapp.dto.account.BankAccountDetailsDto;
import ru.shanalotte.bankbarrel.webapp.dto.account.BankAccountDto;

/**
 * Класс, который преобразует бизнес-объекты класса BankAccount в соответствующее DTO.
 *
 * @see BankAccountDto
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
  public BankAccountDetailsDto convert(BankAccount account) {
    String type = enumToListingDtoItemConverter
        .convert(account.getBankAccountType().name()).getValue();
    String additionalType = enumToListingDtoItemConverter
        .convert(account.getAdditionalType().name()).getValue();
    String currency = account.getCurrency();
    String currencySign = currencyPresentationConverter.currencyToSign(currency);
    String number = account.getNumber();
    String description = account.getDescription();
    String balance = account.getValue().setScale(2, RoundingMode.HALF_UP).toString();
    BankAccountDetailsDto dto = new BankAccountDetailsDto();
    dto.setBalance(balance);
    dto.setAdditionalType(additionalType);
    dto.setCurrency(currency);
    dto.setDescription(description);
    dto.setNumber(number);
    dto.setType(type);
    dto.setCurrencySign(currencySign);
    return dto;
  }
}
