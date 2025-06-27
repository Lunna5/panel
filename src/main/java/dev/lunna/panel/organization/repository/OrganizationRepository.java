package dev.lunna.panel.organization.repository;

import dev.lunna.panel.organization.model.OrganizationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<OrganizationModel, Long> {
  Optional<OrganizationModel> findByName(String name);

  boolean existsByName(String name);

  @Query("SELECT o FROM OrganizationModel o JOIN o.users u WHERE u.id = :userId")
  List<OrganizationModel> findByUserId(@Param("userId") Long userId);
}