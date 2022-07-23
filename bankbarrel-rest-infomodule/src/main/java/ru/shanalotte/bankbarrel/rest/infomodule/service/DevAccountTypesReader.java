package ru.shanalotte.bankbarrel.rest.infomodule.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.BankAccountAdditionalType;
import ru.shanalotte.bankbarrel.core.domain.BankAccountType;
import ru.shanalotte.bankbarrel.core.dto.CodeAndValuePair;
import ru.shanalotte.bankbarrel.core.service.EnumToCodeAndValuePairConverter;

@Service
@Profile({"dev", "test"})
public class DevAccountTypesReader implements AccountTypesReader {

  private EnumToCodeAndValuePairConverter enumToCodeAndValuePairConverter;

  @Autowired
  public DevAccountTypesReader(EnumToCodeAndValuePairConverter enumToCodeAndValuePairConverter) {
    this.enumToCodeAndValuePairConverter = enumToCodeAndValuePairConverter;
  }

  @Override
  public List<CodeAndValuePair> getAccountTypes() {
    List<CodeAndValuePair> codeAndValuePairs = new ArrayList<>();
    try {
      for (BankAccountType bankAccountType : BankAccountType.values()) {
        codeAndValuePairs.add(enumToCodeAndValuePairConverter.convert(bankAccountType.name()));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return codeAndValuePairs;
  }

  @Override
  public List<CodeAndValuePair> getAdditionalAccountTypes(String accountTypeCode) {
    List<CodeAndValuePair> codeAndValuePairs = new ArrayList<>();
    BankAccountType type = BankAccountType.valueOf(accountTypeCode);
    for (BankAccountAdditionalType bankAccountAdditionalType : type.getAdditionalTypes()) {
      codeAndValuePairs.add(
          enumToCodeAndValuePairConverter.convert(bankAccountAdditionalType.name()));
    }
    return codeAndValuePairs;
  }
}
