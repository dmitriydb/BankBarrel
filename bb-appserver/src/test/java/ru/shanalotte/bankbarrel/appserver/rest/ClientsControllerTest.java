package ru.shanalotte.bankbarrel.appserver.rest;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import ru.shanalotte.bankbarrel.appserver.AbstractRestTestCase;
import ru.shanalotte.bankbarrel.appserver.controller.ClientsController;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;

public class ClientsControllerTest extends AbstractRestTestCase {

  @Autowired
  private ClientsController clientsController;

  @Test
  public void gettingClientsList() {
    ResponseEntity<?> response = clientsController.getClientList();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void creatingSomeClient() throws JsonProcessingException {
    BankClientDto bankClientDto = dtoUtils.generateBankClientDto();
    bankClientDto = clientsController.createNewClient(bankClientDto).getBody();
    assertThat(bankClientDto.getId()).isNotNull();
  }

  @Test
  public void emailCanBeMissing() throws JsonProcessingException {
    BankClientDto bankClientDto = dtoUtils.generateBankClientDto();
    bankClientDto.setEmail(null);
    bankClientDto = clientsController.createNewClient(bankClientDto).getBody();
    assertThat(bankClientDto.getId()).isNotNull();
  }

  @Test
  public void telephoneCanBeMissing() throws JsonProcessingException {
    BankClientDto bankClientDto = dtoUtils.generateBankClientDto();
    bankClientDto.setTelephone(null);
    bankClientDto = clientsController.createNewClient(bankClientDto).getBody();
    assertThat(bankClientDto.getId()).isNotNull();
  }

  @Test
  public void bothTelephoneAndEmailCantBeMissing() throws JsonProcessingException {
    BankClientDto bankClientDto = dtoUtils.generateBankClientDto();
    bankClientDto.setTelephone(null);
    bankClientDto.setEmail(null);
    assertThrows(Throwable.class, () -> {
      clientsController.createNewClient(bankClientDto);
    });
  }

  @Test
  @Transactional
  public void gettingExistingClient() {
    List<BankClientDto> existingClients = clientsController.getClientList().getBody();
    Long id = existingClients.get(0).getId();
    ResponseEntity<?> client = clientsController.getClientInfo(id);
    assertThat(client.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void notGettingNotExistingClient() {
    ResponseEntity<?> client = clientsController.getClientInfo(-1L);
    assertThat(client.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void notGettingNotExistingClientNull() {
    ResponseEntity<?> client = clientsController.getClientInfo(null);
    assertThat(client.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  @Transactional
  public void updatingClientInfo() throws JsonProcessingException {
    BankClientDto bankClientDto = dtoUtils.generateBankClientDto();
    bankClientDto = clientsController.createNewClient(bankClientDto).getBody();
    Long id = bankClientDto.getId();
    bankClientDto = dtoUtils.generateBankClientDto();;
    bankClientDto.setId(id);
    clientsController.updateClientInfo(id, bankClientDto);
    BankClientDto dtoAfterUpdate = clientsController.getClientInfo(id).getBody();
    assertThat(dtoAfterUpdate.getGivenName()).isEqualTo(bankClientDto.getGivenName());
    assertThat(dtoAfterUpdate.getFamilyName()).isEqualTo(bankClientDto.getFamilyName());
    assertThat(dtoAfterUpdate.getEmail()).isEqualTo(bankClientDto.getEmail());
    assertThat(dtoAfterUpdate.getTelephone()).isEqualTo(bankClientDto.getTelephone());
  }
}
