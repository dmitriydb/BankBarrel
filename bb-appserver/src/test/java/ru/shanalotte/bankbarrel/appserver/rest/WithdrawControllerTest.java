package ru.shanalotte.bankbarrel.appserver.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.shanalotte.bankbarrel.appserver.AbstractRestTestCase;
import ru.shanalotte.bankbarrel.appserver.controller.DepositController;
import ru.shanalotte.bankbarrel.appserver.controller.WithdrawController;
import ru.shanalotte.bankbarrel.core.dto.DepositDto;
import ru.shanalotte.bankbarrel.core.dto.WithdrawDto;

@SpringBootTest
public class WithdrawControllerTest extends AbstractRestTestCase {


  @Autowired
  private WithdrawController withdrawController;

  @Test
  @Transactional
  public void makingSomeDepositAndFindingIt() throws JsonProcessingException {
    WithdrawDto dto = dtoUtils.generateWithdrawDto();
    dto = withdrawController.createWithdraw(dto).getBody();
    assertThat(dto.getId()).isNotNull();
    WithdrawDto result = withdrawController.withdrawInfo(dto.getId()).getBody();
    assertThat(result.getId()).isEqualTo(dto.getId());
  }
}
