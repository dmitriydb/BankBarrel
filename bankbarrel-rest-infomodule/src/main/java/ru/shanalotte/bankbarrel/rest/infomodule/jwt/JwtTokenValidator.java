package ru.shanalotte.bankbarrel.rest.infomodule.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:jwt-secret.properties")
@Profile({"dev", "production"})
public class JwtTokenValidator {

  private static final Logger logger = LoggerFactory.getLogger(JwtTokenValidator.class);

  @Value("${jwt.token.secret}")
  private String secret;

  @Value("${jwt.validClaims}")
  private List<String> validClaims;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    return passwordEncoder;
  }

  @PostConstruct
  protected void init() {
    logger.info("Initializing JWT secret...");
    secret = Base64.getEncoder().encodeToString(secret.getBytes());
    logger.info("Done!");
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = new User(getServiceName(token), "", new ArrayList<>());
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getServiceName(String token) {
    String serviceName = Jwts.parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
    logger.info("Parsed {} service name from token", serviceName);
    return serviceName;
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    System.out.println(bearerToken);
    if (bearerToken != null) {
      if (bearerToken.startsWith("Bearer")) {
        return bearerToken.substring(7);
      } else {
        return bearerToken;
      }
    }
    logger.error("Resolve token invalid");
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      if (claims.getBody().getExpiration().before(new Date())) {
        logger.error("Token is expired");
        return false;
      }
      String serviceName = (String) claims.getBody().get("servicename");
      if (!validClaims.contains(serviceName)) {
        logger.error("Invalid claim: {}", serviceName);
        return false;
      } else {
        logger.info("Valid claim: {}", serviceName);
      }
      logger.info("Token validated successfully");
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      logger.error("JWT token is expired or invalid");
      throw new RuntimeException("JWT token is expired or invalid");
    }
  }


}