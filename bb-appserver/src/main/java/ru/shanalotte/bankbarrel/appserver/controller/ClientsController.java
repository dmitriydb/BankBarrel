package ru.shanalotte.bankbarrel.appserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shanalotte.bankbarrel.appserver.repository.BankClientDao;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;

@RestController
public class ClientsController {

  private BankClientDao bankClientDao;

  public ClientsController(BankClientDao bankClientDao) {
    this.bankClientDao = bankClientDao;
  }

  @PostMapping("/clients")
  public ResponseEntity<BankClientDto> createNewClient(@RequestBody BankClientDto dto) {
    BankClient client = new BankClient.Builder(dto.getGivenName(), dto.getFamilyName())
        .withEmail(dto.getEmail())
        .withTelephone(dto.getTelephone())
        .build();
    bankClientDao.save(client);
    dto.setId(client.getId());
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
  }

  @GetMapping("/clients/{id}")
  public ResponseEntity<BankClientDto> getClientInfo(@PathVariable("id") Long id) {
    if (!bankClientDao.findById(id).isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    BankClient client = bankClientDao.getById(id);
    BankClientDto dto = new BankClientDto();
    dto.setId(client.getId());
    dto.setEmail(client.getEmail());
    dto.setFamilyName(client.getFamilyName());
    dto.setTelephone(client.getTelephone());
    dto.setGivenName(client.getGivenName());
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @PutMapping("/clients/{id}")
  public ResponseEntity<BankClientDto> getClientInfo(@PathVariable("id") Long id, @RequestBody BankClientDto dto) {
    if (!bankClientDao.findById(id).isPresent()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    BankClient client = bankClientDao.getById(id);
    client.setEmail(dto.getEmail());
    client.setGivenName(dto.getGivenName());
    client.setFamilyName(dto.getFamilyName());
    client.setTelephone(dto.getTelephone());
    bankClientDao.save(client);
    dto.setId(client.getId());
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }
}
