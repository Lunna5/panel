package dev.lunna.panel.security.service;

import dev.lunna.panel.security.config.JWTSettings;
import dev.lunna.panel.security.exception.TokenExpiredException;
import dev.lunna.panel.security.model.SessionModel;
import dev.lunna.panel.security.repository.SessionRepository;
import dev.lunna.panel.user.ApplicationUser;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class JwtService {
  private final JWTSettings settings;
  private final SecretKey key;
  private final SessionRepository sessionRepository;

  public JwtService(
      @NotNull final JWTSettings settings,
      @NotNull final SessionRepository sessionRepository
  ) {
    this.settings = settings;
    this.sessionRepository = sessionRepository;
    this.key = getSigningKey();
  }

  private SecretKey getSigningKey() {
    byte[] keyBytes = settings.getSecret().getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String generateToken(@NotNull final SessionModel model) {
    Map<String, Object> claims = new HashMap<>();

    return createToken(claims, model.getId().toString());
  }

  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .claims(claims)
        .subject(subject)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + settings.getExpiration()))
        .signWith(getSigningKey())
        .compact();
  }

  public Long extractSessionID(String token) {
    try {
      return Long.parseLong(Jwts
          .parser()
          .verifyWith(key)
          .build()
          .parseSignedClaims(token)
          .getPayload()
          .getSubject()
      );
    } catch (JwtException e) {
      throw new TokenExpiredException();
    }
  }

  @Transactional
  public boolean isTokenValid(String token, ApplicationUser user) {
    final long sessionId = extractSessionID(token);

    SessionModel session = sessionRepository.findById(sessionId)
        .orElseThrow(TokenExpiredException::new);

    if (!Objects.equals(session.getUser().getId(), user.getUser().getId())) {
      throw new TokenExpiredException();
    }

    if (isTokenExpired(token)) {
      throw new TokenExpiredException();
    }

    return true;
  }

  private boolean isTokenExpired(String token) {
    return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getExpiration()
        .before(new Date());
  }
}