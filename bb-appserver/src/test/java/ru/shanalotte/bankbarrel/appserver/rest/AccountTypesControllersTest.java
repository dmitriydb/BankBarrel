package ru.shanalotte.bankbarrel.appserver.rest;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import ru.shanalotte.bankbarrel.appserver.AbstractRestTestCase;
import ru.shanalotte.bankbarrel.appserver.controller.AccountTypesController;
import ru.shanalotte.bankbarrel.core.dto.AccountAdditionalTypeDto;
import ru.shanalotte.bankbarrel.core.dto.AccountTypeDto;

public class AccountTypesControllersTest extends AbstractRestTestCase {

  @Autowired
  private AccountTypesController accountTypesController;

  @Test
  public void existingTypesAreSorted() {
    List<AccountTypeDto> accountTypeDtoList = accountTypesController.getAccountTypes().getBody();
    assertThat(accountTypeDtoList).isSortedAccordingTo((a, b) -> (int) (a.getId() - b.getId()));
  }

  @Test
  public void type200() {
    ResponseEntity<?> response = accountTypesController.getAccountTypeByCode("CHECKING");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void type404() {
    ResponseEntity<?> response = accountTypesController.getAccountTypeByCode("xxx");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void subTypes404() {
    ResponseEntity<?> response = accountTypesController.getAdditionalTypes("xxx");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void subTypes200() {
    ResponseEntity<?> response = accountTypesController.getAdditionalTypes("CHECKING");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void subType404() {
    ResponseEntity<?> response = accountTypesController.getAdditionalTypeInfo("xxx");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void subType200() {
    ResponseEntity<?> response = accountTypesController.getAdditionalTypeInfo("PREMIUM");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  @Transactional
  public void creatingAndDeletingSomeSubtype() throws JsonProcessingException {
    AccountAdditionalTypeDto subTypeDto = dtoUtils.generateRandomAdditionalAccountTypeDto();
    int subTypesBefore = accountTypesController.getAdditionalTypes("CHECKING").getBody().size();
    subTypeDto = accountTypesController.createAdditionalType("CHECKING", subTypeDto).getBody();
    int subTypesAfter = accountTypesController.getAdditionalTypes("CHECKING").getBody().size();
    assertThat(subTypesAfter - subTypesBefore).isOne();
    accountTypesController.deleteAdditionalType("CHECKING", subTypeDto);
    subTypesAfter = accountTypesController.getAdditionalTypes("CHECKING").getBody().size();
    assertThat(subTypesAfter).isEqualTo(subTypesBefore);
  }

  @Test
  @Transactional
  public void creatingAndDeletingSomeType() throws JsonProcessingException {
    int before = accountTypesController.getAccountTypes().getBody().size();
    AccountTypeDto dto = dtoUtils.generateRandomBankTypeDto();
    dto = accountTypesController.createNewAccountType(dto).getBody();
    accountTypesController.deleteAccountType(dto);
    int after = accountTypesController.getAccountTypes().getBody().size();
    assertThat(after).isEqualTo(before);
  }

  @Test
  @Transactional
  public void creatingTypeConflict() throws JsonProcessingException {
    AccountTypeDto dto = dtoUtils.generateRandomBankTypeDto();
    dto = accountTypesController.createNewAccountType(dto).getBody();
    ResponseEntity<?> response = accountTypesController.createNewAccountType(dto);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    accountTypesController.deleteAccountType(dto);
  }

  @Test
  @Transactional
  public void creatingAdditionalTypeConflict() throws JsonProcessingException {
    AccountAdditionalTypeDto dto = dtoUtils.generateRandomAdditionalAccountTypeDto();
    dto = accountTypesController.createAdditionalType("CHECKING", dto).getBody();
    ResponseEntity<?> response = accountTypesController.createAdditionalType("CHECKING", dto);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    accountTypesController.deleteAdditionalType("CHECKING", dto);
  }

}
