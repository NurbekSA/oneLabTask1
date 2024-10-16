package org.example.service;

import org.example.model.AdminModel;
import org.example.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepo adminRepository;

    @Autowired
    public AdminService(AdminRepo adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<AdminModel> findAll() {
        return adminRepository.findAll(); // Получить всех администраторов
    }

    public Optional<AdminModel> findById(Long id) {
        return adminRepository.findById(id); // Найти администратора по ID
    }

    public AdminModel save(AdminModel admin) {
        return adminRepository.save(admin); // Сохранить администратора
    }

    public void deleteById(Long id) {
        adminRepository.deleteById(id); // Удалить администратора по ID
    }

    public Optional<AdminModel> findByUsername(String username) {
        return adminRepository.findByUsername(username); // Найти администратора по имени пользователя
    }

    public Optional<AdminModel> findByEmail(String email) {
        return adminRepository.findByEmail(email); // Найти администратора по email
    }
}
