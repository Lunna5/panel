package dev.lunna.panel.security.service.hasher;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BCryptPasswordHasherService implements PasswordHasherService {
  private final BCryptPasswordEncoder passwordEncoder;

  public BCryptPasswordHasherService() {
    this.passwordEncoder = new BCryptPasswordEncoder(); // strength can be customized
  }

  @Override
  public String hash(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }

  @Override
  public boolean matches(String rawPassword, String hashedPassword) {
    return passwordEncoder.matches(rawPassword, hashedPassword);
  }
}