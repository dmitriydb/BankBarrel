package ru.shanalotte.jwt.provider;

import java.util.Arrays;
import java.util.Map;
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
@PropertySource("classpath:jwt-credentials-store.properties")
public class JwtProviderLauncher implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(JwtProviderLauncher.class);

  @Autowired
  private UserRepository userRepository;

  @Value("#{${valid-credentials}}")
  private Map<String, String> validCredentials;

  public static void main(String[] args) {
    SpringApplication.run(JwtProviderLauncher.class, args);
  }

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    logger.info("loading services jwt credentials into memory...");
    long nextId = 1;
    for (String serviceName : validCredentials.keySet()) {
      logger.info("Loading {}", serviceName);
      userRepository.save(new User(nextId++, serviceName, passwordEncoder.encode(validCredentials.get(serviceName))));
    }
    logger.info("Done!");
  }
}
