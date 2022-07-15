package ru.shanalotte.bankbarrel.rest.infomodule.endpoints;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.shanalotte.bankbarrel.rest.infomodule.listener.MicroserviceRegistryDeployingInfoSender;

@SpringBootTest
@ActiveProfiles("production")
@AutoConfigureMockMvc
public class EndpointsProductionTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  MicroserviceRegistryDeployingInfoSender microserviceRegistryDeployingInfoSender;

  @MockBean
  JedisConnectionFactory jedisConnectionFactory;

  @Test
  public void accountTypesAre403() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/accounttypes")).andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  public void additionalAccountTypesAre403() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/accounttype/SAVING/additionaltypes")).andExpect(MockMvcResultMatchers.status().isForbidden());
  }

}
