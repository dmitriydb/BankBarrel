package ru.shanalotte.jwt.provider.credentials;

import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import ru.shanalotte.jwt.provider.controller.JwtController;
import ru.shanalotte.jwt.provider.dto.AuthDto;
import ru.shanalotte.jwt.provider.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class CredentialsLoadingTest {

  @Autowired
  private MockMvc mockMvc;

  @Value("#{${valid-credentials}}")
  private Map<String, String> validCredentials;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtController jwtController;

  @Test
  public void shouldCorrectlyLoadAllJwtCredentials() {
    for (String username: validCredentials.keySet()) {
      assertThat(userRepository.findByUsername(username)).isNotNull();
    }
  }

  @Test
  public void shouldIssueTokenToValidCredentialsRequest() {
    for (String username: validCredentials.keySet()) {
      AuthDto authDto = new AuthDto();
      authDto.setUsername(username);
      authDto.setPassword(validCredentials.get(username));
      System.out.println(jwtController.login(authDto).getBody());
    }
  }

  @Test
  public void shouldNotIssueTokenToWrongCredentials() {
    AuthDto authDto = new AuthDto();
    authDto.setUsername("a");
    authDto.setPassword("missing");
    assertThrows(Throwable.class, () -> {
      jwtController.login(authDto);
    });
  }

  @Test
  public void shouldNotIssueTokenToMissingCredentials() {
    AuthDto authDto = new AuthDto();
    authDto.setUsername("a");
    authDto.setPassword("missing");
    assertThrows(Throwable.class, () -> {
      jwtController.login(authDto);
    });
  }
}
