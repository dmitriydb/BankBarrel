package ru.shanalotte.bankbarrel.webapp.testutils;

import ru.shanalotte.bankbarrel.webapp.dto.account.AccountOpeningDto;

public class TestDtoFactory {

  public static AccountOpeningDto accountOpeningDto() {
    AccountOpeningDto accountOpeningDto = new AccountOpeningDto();
    accountOpeningDto.setAccountType("CHECKING");
    accountOpeningDto.setAccountAdditionalType("PREMIUM");
    accountOpeningDto.setCurrency("USD");
    return accountOpeningDto;
  }
}
