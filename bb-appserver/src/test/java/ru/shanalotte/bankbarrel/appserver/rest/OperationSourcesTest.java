package ru.shanalotte.bankbarrel.appserver.rest;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.shanalotte.bankbarrel.appserver.AbstractRestTestCase;
import ru.shanalotte.bankbarrel.appserver.controller.OperationSourcesController;

public class OperationSourcesTest extends AbstractRestTestCase {

  @Autowired
  private OperationSourcesController operationSourcesController;

  @Test
  public void fetchingAllOperationSources() {
    ResponseEntity<?> sources = operationSourcesController.getOperationSources();
    assertThat(sources.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

}
