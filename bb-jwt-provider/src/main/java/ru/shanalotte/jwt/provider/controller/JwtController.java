package ru.shanalotte.jwt.provider.controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.jwt.provider.dto.AuthDto;
import ru.shanalotte.jwt.provider.entity.User;
import ru.shanalotte.jwt.provider.repository.UserRepository;
import ru.shanalotte.jwt.provider.service.JwtTokenProvider;

@RestController
public class JwtController {

  private static final Logger logger = LoggerFactory.getLogger(JwtController.class);

  private final AuthenticationManager authenticationManager;

  private final JwtTokenProvider jwtTokenProvider;

  private final UserRepository userRepository;

  @Autowired
  public JwtController(AuthenticationManager authenticationManager,
                       JwtTokenProvider jwtTokenProvider,
                       UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
    this.userRepository = userRepository;
  }

  @PostMapping("/auth")
  public ResponseEntity login(@RequestBody AuthDto requestDto) {
    logger.info("Service name {} trying to get token", requestDto.getUsername());
    try {
      String username = requestDto.getUsername();
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
      User user = userRepository.findByUsername(username);
      if (user == null) {
        logger.error("Service name {} is not registered in JWT provider", requestDto.getUsername());
        throw new UsernameNotFoundException("User with username: " + username + " not found");
      }
      String token = jwtTokenProvider.createToken(username);
      Map<Object, Object> response = new HashMap<>();
      response.put("servicename", username);
      response.put("token", token);
      logger.info("Successfully created JWT token for {}", requestDto.getUsername());
      return ResponseEntity.ok(response);
    } catch (AuthenticationException e) {
      logger.error("Wrong password for {}", requestDto.getUsername());
      throw new BadCredentialsException("Invalid username or password");
    }
  }
}
