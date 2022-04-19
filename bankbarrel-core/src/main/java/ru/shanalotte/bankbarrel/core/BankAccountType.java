package ru.shanalotte.bankbarrel.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class represents bank account main types.
 * For the reference: https://www.ramseysolutions.com/banking/types-of-bank-accounts
 */
public enum BankAccountType {

  CHECKING(new HashSet<>(Arrays.asList(
      BankAccountAdditionalType.TRADITIONAL,
      BankAccountAdditionalType.PREMIUM,
      BankAccountAdditionalType.INTEREST_BEARING,
      BankAccountAdditionalType.REWARDS,
      BankAccountAdditionalType.STUDENT,
      BankAccountAdditionalType.SECOND_CHANCE
  ))),

  SAVING(new HashSet<>(Collections.singletonList(
      BankAccountAdditionalType.SAVINGS_ONLY
  )));

  private Set<BankAccountAdditionalType> additionalTypes;

  BankAccountType(Set<BankAccountAdditionalType> additionalTypes) {
    this.additionalTypes = additionalTypes;
  }

  public Set<BankAccountAdditionalType> getAdditionalTypes() {
    return additionalTypes;
  }
}
