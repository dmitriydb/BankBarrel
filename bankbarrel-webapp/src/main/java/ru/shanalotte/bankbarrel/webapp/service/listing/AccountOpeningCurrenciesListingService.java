package ru.shanalotte.bankbarrel.webapp.service.listing;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.dto.ListingDto;
import ru.shanalotte.bankbarrel.core.dto.ListingDtoItem;
import ru.shanalotte.bankbarrel.webapp.service.converter.CurrencyPresentationConverter;

/**
 * Сервис, который возвращает список возможных валют для открытия счета.
 */
@Service
public class AccountOpeningCurrenciesListingService implements ListingService {

  private CurrencyPresentationConverter currencyPresentationConverter;
  @Value("${bank.account.opening.currencies}")
  private List<String> bankOpeningCurrencies;

  public AccountOpeningCurrenciesListingService(CurrencyPresentationConverter
                                                    currencyPresentationConverter) {
    this.currencyPresentationConverter = currencyPresentationConverter;
  }

  @Override
  public ListingDto getListingDto() {
    ListingDto dto = new ListingDto();
    for (String currency : bankOpeningCurrencies) {
      String code = currency;
      String currencySign = currencyPresentationConverter.currencyToSign(currency);
      String currencyName = currencyPresentationConverter.currencyToName(currency);
      String value = currencyName + " (" + currencySign + ")";
      dto.addItem(new ListingDtoItem(code, value));
    }
    return dto;
  }
}
