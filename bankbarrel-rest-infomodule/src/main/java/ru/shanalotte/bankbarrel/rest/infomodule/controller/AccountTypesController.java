package ru.shanalotte.bankbarrel.rest.infomodule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.bankbarrel.core.domain.BankAccountAdditionalType;
import ru.shanalotte.bankbarrel.core.domain.BankAccountType;
import ru.shanalotte.bankbarrel.core.dto.ListingDtoItem;
import ru.shanalotte.bankbarrel.core.service.EnumToListingDtoItemConverter;

/**
 * Контроллер, который возвращает json с возможными типами банковских счетов 1 и 2 уровня.
 */
@RestController
public class AccountTypesController {

  private EnumToListingDtoItemConverter enumToListingDtoItemConverter;

  public AccountTypesController(EnumToListingDtoItemConverter enumToListingDtoItemConverter) {
    this.enumToListingDtoItemConverter = enumToListingDtoItemConverter;
  }

  /**
   * Возвращает json со всеми типами счетов 1 уровня.
   */
  @CrossOrigin(origins = "http://localhost:8888")
  @GetMapping("/accounttypes")
  @Operation(description = "Получить названия типов банковских счетов")
  public List<ListingDtoItem> accountTypes() {
    List<ListingDtoItem> listingDtoItems = new ArrayList<>();
    try {
      for (BankAccountType bankAccountType : BankAccountType.values()) {
        listingDtoItems.add(enumToListingDtoItemConverter.convert(bankAccountType.name()));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return listingDtoItems;
  }

  /**
   * Возвращает json с типами счетов 2 уровня.
   *
   * @param code код типа 1 уровня
   */
  @CrossOrigin(origins = "http://localhost:8888")
  @GetMapping("/accounttype/{code}/additionaltypes")
  @Operation(description = "Получить названия типов банковских счетов 2 уровня")
  @ApiResponse(responseCode = "404", description = "Возвращает в случае, "
      + "если тип банковского счета 1 уровня с таким кодом не существует")
  public ResponseEntity<List<ListingDtoItem>> additionalTypes(
      @Parameter(description = "Тип счета 1 уровня")
                  @PathVariable("code") String code) {
    List<ListingDtoItem> listingDtoItems = new ArrayList<>();
    try {
      BankAccountType type = BankAccountType.valueOf(code);
      for (BankAccountAdditionalType bankAccountAdditionalType : type.getAdditionalTypes()) {
        listingDtoItems.add(
            enumToListingDtoItemConverter.convert(bankAccountAdditionalType.name()));
      }
    } catch (IllegalArgumentException ex) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(listingDtoItems, HttpStatus.OK);
  }
}
