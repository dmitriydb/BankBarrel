package ru.shanalotte.bankbarrel.webapp.dao.impl.operations;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperationStatus;
import ru.shanalotte.bankbarrel.webapp.entities.WebAppOperationType;

@SpringBootTest
@AutoConfigureMockMvc
public class WebAppOperationTypeDaoTest {

  @Autowired
  private WebAppOperationTypeDao webAppOperationTypeDao;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void tryFindAll() {
    List<WebAppOperationType> all = webAppOperationTypeDao.findAll();
    System.out.println(all);

    for (String name : all.stream().map(e -> e.getName()).collect(Collectors.toList())) {
      System.out.println(webAppOperationTypeDao.findByName(name));
    }
  }
}