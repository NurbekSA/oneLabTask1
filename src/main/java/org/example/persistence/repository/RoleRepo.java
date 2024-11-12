package org.example.persistence.repository;

import org.example.persistence.model.entity.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<RoleModel, Long> {
    Optional<RoleModel> findByName(String name);
}
