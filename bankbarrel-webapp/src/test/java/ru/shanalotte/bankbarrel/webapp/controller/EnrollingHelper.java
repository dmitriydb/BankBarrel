package ru.shanalotte.bankbarrel.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

@Service
public class EnrollingHelper {

  @Autowired
  private MockMvc mockMvc;

  public void enrollSomeUser() throws Exception {
    mockMvc.perform(post("/enroll")
        .param("username", "testuser")
        .param("firstName", "v")
        .param("lastName", "a")
        .param("email", "a@xcyz")
    );
  }

  public void enrollUser(String name) throws Exception {
    mockMvc.perform(post("/enroll")
        .param("username", name)
        .param("firstName", name)
        .param("lastName", name)
        .param("email", name + "@" + name)
    );
  }
}
