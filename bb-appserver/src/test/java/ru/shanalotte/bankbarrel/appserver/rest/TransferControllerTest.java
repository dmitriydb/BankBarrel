package ru.shanalotte.bankbarrel.appserver.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.shanalotte.bankbarrel.appserver.AbstractRestTestCase;
import ru.shanalotte.bankbarrel.appserver.controller.TransferController;
import ru.shanalotte.bankbarrel.appserver.controller.WithdrawController;
import ru.shanalotte.bankbarrel.core.dto.TransferDto;
import ru.shanalotte.bankbarrel.core.dto.WithdrawDto;

@SpringBootTest
public class TransferControllerTest extends AbstractRestTestCase {


  @Autowired
  private TransferController transferController;

  @Test
  @Transactional
  public void makingSomeTransferndFindingIt() throws JsonProcessingException {
    TransferDto dto = dtoUtils.generateTransferDto();
    dto = transferController.createTransfer(dto).getBody();
    assertThat(dto.getId()).isNotNull();
    TransferDto result = transferController.transferInfo(dto.getId()).getBody();
    assertThat(result.getId()).isEqualTo(dto.getId());
  }
}
