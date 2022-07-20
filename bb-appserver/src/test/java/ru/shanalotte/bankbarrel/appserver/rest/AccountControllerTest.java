package ru.shanalotte.bankbarrel.appserver.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import ru.shanalotte.bankbarrel.appserver.AbstractRestTestCase;
import ru.shanalotte.bankbarrel.appserver.controller.AccountController;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountDao;
import ru.shanalotte.bankbarrel.appserver.repository.BankClientDao;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;

public class AccountControllerTest extends AbstractRestTestCase {

  @Autowired
  private AccountController accountController;

  @Autowired
  private BankClientDao bankClientDao;

  @Autowired
  private BankAccountDao bankAccountDao;

  @Test
  public void readingAllAccounts() throws Exception {
    accountController.getAllAccounts();
  }


  @Test
  public void creatingAccountThenFindingIt() throws JsonProcessingException {
    BankAccountDto bankAccountDto = dtoUtils.generateRandomBankAccountDto();
    bankAccountDto = accountController.createAccount(bankAccountDto).getBody();
    ResponseEntity<BankAccountDto> response = accountController.getAccountInfo(bankAccountDto.getNumber());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    accountController.deleteAccount(bankAccountDto.getIdentifier());
  }

  @Test
  public void should404whenAccountNotExists() {
    ResponseEntity<BankAccountDto> response = accountController.getAccountInfo("missing");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void deletingAccountById200() throws JsonProcessingException {
    BankAccountDto bankAccountDto = dtoUtils.generateRandomBankAccountDto();
    bankAccountDto = accountController.createAccount(bankAccountDto).getBody();
    ResponseEntity<?> response = accountController.deleteAccount(bankAccountDto.getIdentifier());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void deletingAccountById404() {
    ResponseEntity<?> response = accountController.deleteAccount("-1L");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  @Transactional
  public void obtainingClientsAccounts() throws JsonProcessingException {
    BankAccountDto bankAccountDto = dtoUtils.generateRandomBankAccountDto();
    int accountsBefore = accountController.getClientAccounts(bankAccountDto.getOwner()).getBody().size();
    accountController.createAccount(bankAccountDto).getBody();
    int accountsAfterAdding = accountController.getClientAccounts(bankAccountDto.getOwner()).getBody().size();
    assertThat(accountsAfterAdding - accountsBefore).isOne();
    accountController.deleteAccount(bankAccountDto.getIdentifier());
    int accountsAfterDeletion = accountController.getClientAccounts(bankAccountDto.getOwner()).getBody().size();
    assertThat(accountsAfterDeletion).isEqualTo(accountsBefore);
  }

  @Test
  public void missingClientId404() {
    ResponseEntity<?> response = accountController.getClientAccounts(-1L);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void dtoMissingClientId() throws JsonProcessingException {
    BankAccountDto bankAccountDto = dtoUtils.generateRandomBankAccountDto();
    bankAccountDto.setOwner(null);
    ResponseEntity<?> response = accountController.createAccount(bankAccountDto);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void dtoWrongClientId() throws JsonProcessingException {
    BankAccountDto bankAccountDto = dtoUtils.generateRandomBankAccountDto();
    bankAccountDto.setOwner(-100L);
    ResponseEntity<?> response = accountController.createAccount(bankAccountDto);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void dtoMissingAccountType() throws JsonProcessingException {
    BankAccountDto bankAccountDto = dtoUtils.generateRandomBankAccountDto();
    bankAccountDto.setType(null);
    ResponseEntity<?> response = accountController.createAccount(bankAccountDto);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void dtoWrongAccountType() throws JsonProcessingException {
    BankAccountDto bankAccountDto = dtoUtils.generateRandomBankAccountDto();
    bankAccountDto.setType("xyz");
    ResponseEntity<?> response = accountController.createAccount(bankAccountDto);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void dtoMissingAdditionalAccountType() throws JsonProcessingException {
    BankAccountDto bankAccountDto = dtoUtils.generateRandomBankAccountDto();
    bankAccountDto.setAdditionalType(null);
    ResponseEntity<?> response = accountController.createAccount(bankAccountDto);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void dtoWrongAdditionalAccountType() throws JsonProcessingException {
    BankAccountDto bankAccountDto = dtoUtils.generateRandomBankAccountDto();
    bankAccountDto.setAdditionalType("xyz");
    ResponseEntity<?> response = accountController.createAccount(bankAccountDto);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void dtoMissingCurrency() throws JsonProcessingException {
    BankAccountDto bankAccountDto = dtoUtils.generateRandomBankAccountDto();
    bankAccountDto.setCurrency(null);
    ResponseEntity<?> response = accountController.createAccount(bankAccountDto);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void dtoWrongCurrency() throws JsonProcessingException {
    BankAccountDto bankAccountDto = dtoUtils.generateRandomBankAccountDto();
    bankAccountDto.setCurrency("xyz");
    ResponseEntity<?> response = accountController.createAccount(bankAccountDto);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void conflictWhenCreatingAccountWithSameNumber() throws JsonProcessingException {
    BankAccountDto bankAccountDto = dtoUtils.generateRandomBankAccountDto();
    ResponseEntity<?> response = accountController.createAccount(bankAccountDto);
    ResponseEntity<?> response2 = accountController.createAccount(bankAccountDto);
    assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
  }

}
