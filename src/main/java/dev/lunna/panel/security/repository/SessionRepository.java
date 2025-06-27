package dev.lunna.panel.security.repository;

import dev.lunna.panel.security.model.SessionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

public interface SessionRepository extends JpaRepository<SessionModel, Long> {
  List<SessionModel> findAllByUserId(Long userId);

  // @Query("""
  //     SELECT s FROM SessionModel s
  //     WHERE s.userId = :userId
  //     AND (s.expiresAt IS NULL OR s.expiresAt > :currentTime)
  //     """)
  // List<SessionModel> findActiveSessionsByUserId(
  //     @Param("userId") Long userId,
  //     @Param("currentTime") Instant currentTime
  // );

  // @Transactional
  // @Modifying
  // @Query("DELETE FROM SessionModel s WHERE s.expiresAt < :currentTime")
  // void deleteExpiredSessions(@Param("currentTime") Instant currentTime);
}
