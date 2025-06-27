package dev.lunna.panel.security.service.hasher;

public interface PasswordHasherService {
  String hash(String rawPassword);
  boolean matches(String rawPassword, String hashedPassword);
}