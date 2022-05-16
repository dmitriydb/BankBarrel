package ru.shanalotte.bankbarrel.appserver.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.bankbarrel.appserver.domain.OperationSource;
import ru.shanalotte.bankbarrel.appserver.repository.OperationSourceDao;
import ru.shanalotte.bankbarrel.core.dto.OperationSourceDto;

@RestController
public class OperationSourcesController {

  private OperationSourceDao operationSourceDao;

  public OperationSourcesController(OperationSourceDao operationSourceDao) {
    this.operationSourceDao = operationSourceDao;
  }

  @GetMapping("/operations/sources")
  public ResponseEntity<List<OperationSourceDto>> getOperationSources() {
    List<OperationSourceDto> dtos = operationSourceDao.findAll()
        .stream().map(entity -> {
          OperationSourceDto dto = new OperationSourceDto();
          dto.setId(entity.getId());
          dto.setName(entity.getName());
          return dto;
        }).collect(Collectors.toList());
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

}
