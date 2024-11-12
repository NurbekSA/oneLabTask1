package org.example.persistence.repository;

import org.example.persistence.model.entity.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimpleUserRepo extends JpaRepository<SimpleUser, Long> {
    Optional<SimpleUser> findByUsername(String username);
    boolean existsByUsername(String username);

}
