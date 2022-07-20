package ru.shanalotte.bankbarrel.rest.infomodule.service;

import java.util.List;
import ru.shanalotte.bankbarrel.core.dto.CodeAndValuePair;

@SuppressWarnings("checkstyle:MissingJavadocType")
public interface AccountTypesReader {

  List<CodeAndValuePair> getAccountTypes();

  List<CodeAndValuePair> getAdditionalAccountTypes(String accountTypeCode);

}
