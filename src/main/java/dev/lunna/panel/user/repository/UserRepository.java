package dev.lunna.panel.user.repository;

import dev.lunna.panel.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
  Optional<UserModel> findByEmail(String email);

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);

  Optional<UserModel> findByUsername(String username);
}
