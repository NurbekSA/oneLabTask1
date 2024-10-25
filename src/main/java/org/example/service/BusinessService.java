package org.example.service;

import org.example.model.BusinessModel;
import org.example.repository.BusinessRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService {
    private final BusinessRepo businessRepo;

    @Autowired
    public BusinessService(BusinessRepo businessModelRepository) {
        this.businessRepo = businessModelRepository;
    }

    // Получить все записи
    public ResponseEntity<List<BusinessModel>> findAll() {
        List<BusinessModel> businesses = businessRepo.findAll();
        return ResponseEntity.ok(businesses);
    }

    // Получить запись по ID
    public ResponseEntity<BusinessModel> findById(Long id) {
        BusinessModel businessModel = businessRepo.findById(id).orElse(null);
        if (businessModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(businessModel);
    }

    // Создать новую запись
    public ResponseEntity<?> create(BusinessModel businessModel) {
        businessModel.setCreatedAt(System.currentTimeMillis()); // Устанавливаем дату создания
        businessModel.setUpdatedAt(System.currentTimeMillis()); // Устанавливаем дату обновления
        BusinessModel savedBusiness = businessRepo.save(businessModel);
        return ResponseEntity.ok(savedBusiness);
    }

}
