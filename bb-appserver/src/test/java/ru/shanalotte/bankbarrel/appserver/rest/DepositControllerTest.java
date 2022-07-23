package ru.shanalotte.bankbarrel.appserver.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.shanalotte.bankbarrel.appserver.AbstractRestTestCase;
import ru.shanalotte.bankbarrel.appserver.controller.DepositController;
import ru.shanalotte.bankbarrel.core.dto.DepositDto;

@SpringBootTest
public class DepositControllerTest extends AbstractRestTestCase {


  @Autowired
  private DepositController depositController;

  @Test
  @Transactional
  public void makingSomeDepositAndFindingIt() throws JsonProcessingException {
    DepositDto dto = dtoUtils.generateDepositDto();
    dto = depositController.createDeposit(dto).getBody();
    assertThat(dto.getId()).isNotNull();
    DepositDto result = depositController.depositInfo(dto.getId()).getBody();
    assertThat(result.getId()).isEqualTo(dto.getId());
  }
}
