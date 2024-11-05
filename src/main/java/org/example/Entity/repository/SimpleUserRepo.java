package org.example.entity.repository;

import org.example.entity.model.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimpleUserRepo extends JpaRepository<SimpleUser, Long> {
    Optional<SimpleUser> findSimpleUserByUsername(String username);
}
