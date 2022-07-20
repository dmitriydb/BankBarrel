package ru.shanalotte.jwt.provider.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.shanalotte.jwt.provider.entity.User;
import ru.shanalotte.jwt.provider.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User u = userRepository.findByUsername(username);
    return new org.springframework.security.core.userdetails.User(
        u.getUsername(), u.getPassword(), new ArrayList<>());
  }
}
