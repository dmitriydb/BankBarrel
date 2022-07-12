package ru.shanalotte.jwt.provider;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.shanalotte.jwt.provider.entity.User;
import ru.shanalotte.jwt.provider.repository.UserRepository;

@SpringBootApplication
@ComponentScan("ru.shanalotte.jwt.provider")
@PropertySource("jwt-credentials-store.properties")
public class JwtProviderLauncher implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(JwtProviderLauncher.class);

  @Autowired
  private UserRepository userRepository;

  @Value("${bb-webapp}")
  private String webappPassword;

  public static void main(String[] args) {
    SpringApplication.run(JwtProviderLauncher.class, args);
  }

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    logger.info("loading services jwt credentials into memory...");
    userRepository.saveAll(Arrays.asList(
        new User(1L, "bb-webapp", passwordEncoder.encode(webappPassword))
    ));
    logger.info("Done!");
  }
}
