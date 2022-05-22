package ru.shanalotte.bankbarrel.webapp.dao.impl.operations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;
import org.springframework.test.web.servlet.MockMvc;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperation;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperationHistory;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperationStatus;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

@SpringBootTest
@AutoConfigureMockMvc
public class WebAppOperationDaoTest {

  @Autowired
  private WebAppOperationDao webAppOperationDao;

  @Autowired
  private WebAppUserDao webAppUserDao;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private WebAppOperationHistoryDao webAppOperationHistoryDao;

  @Test
  public void tryCreateOperation() {
    WebAppOperationHistory webAppOperationHistory = webAppOperationHistoryDao.findById(1L).get();
    webAppOperationHistoryDao.closeEntry(webAppOperationHistory.getId());
    webAppOperationHistory.setStatus("WRONGDATA");
    webAppOperationHistoryDao.createEntry(webAppOperationHistory);
  }
}