package org.example.repository;


import org.example.model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<AdminModel, Long> {
    Optional<AdminModel> findByUsername(String username); // Метод для поиска администратора по имени пользователя
    Optional<AdminModel> findByEmail(String email); // Метод для поиска администратора по email
}