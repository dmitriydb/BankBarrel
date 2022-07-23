package ru.shanalotte.jwt.provider.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.shanalotte.jwt.provider.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  User findByUsername(String username);
}
