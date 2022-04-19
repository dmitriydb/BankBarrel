package ru.shanalotte.bankbarrel.core;

import static ru.shanalotte.bankbarrel.core.CustomerCreationData.validName;
import static ru.shanalotte.bankbarrel.core.CustomerCreationData.validSurname;

public class DummyService {

  public static Customer createDummyCustomer(){
    return new Customer.Builder(validName, validSurname)
        .withEmail("abc@xdf")
        .build();
  }

  public static BankAccount createDummyCheckingBankAccount() {
    return new BankAccount.Builder()
        .withOwner(createDummyCustomer())
        .withType(BankAccountType.CHECKING)
        .withAdditionalType(BankAccountAdditionalType.TRADITIONAL)
        .build();
  }
}
