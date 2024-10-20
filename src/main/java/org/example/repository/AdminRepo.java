package org.example.repository;


import org.example.model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<AdminModel, Long> {
    Optional<AdminModel> findByUsername(String username);
    Optional<AdminModel> findByEmail(String email);
}

