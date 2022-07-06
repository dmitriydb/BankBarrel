package ru.shanalotte.bankbarrel.appserver.controller;

import java.util.List;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.bankbarrel.appserver.domain.OperationSource;
import ru.shanalotte.bankbarrel.appserver.repository.OperationSourceDao;
import ru.shanalotte.bankbarrel.core.dto.OperationSourceDto;

/**
 * Контроллер для операция с источниками операций с API.
 */
@RestController
@Tag(name = "OperationSources", description = "Источники операций")
public class OperationSourcesController {

  private OperationSourceDao operationSourceDao;
  private static final Logger logger = LoggerFactory.getLogger(OperationSourcesController.class);

  public OperationSourcesController(OperationSourceDao operationSourceDao) {
    this.operationSourceDao = operationSourceDao;
  }

  /**
   * Получение всех возможных источников операций с WEB API.
   */
  @Operation(summary = "Получить список всех источников операций")
  @GetMapping("/operations/sources")
  public ResponseEntity<List<OperationSourceDto>> getOperationSources() {
    logger.info("GET /operations/sources");
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
