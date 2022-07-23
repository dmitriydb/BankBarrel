package ru.shanalotte.bankbarrel.webapp.dao.impl.operations;

import java.util.List;
import java.util.stream.Collectors;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperationStatus;

@SpringBootTest
@AutoConfigureMockMvc
public class WebAppOperationStatusDaoTest {

  @Autowired
  private WebAppOperationStatusDao webAppOperationStatusDao;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void tryFindAll() {
    List<WebAppOperationStatus> all = webAppOperationStatusDao.findAll();
    System.out.println(all);

    for (String status : all.stream().map(e -> e.getStatus()).collect(Collectors.toList())) {
      System.out.println(webAppOperationStatusDao.findByStatus(status));
    }
  }
}