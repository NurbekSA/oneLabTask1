package org.example.service;

import org.example.model.BusinessModel;
import org.example.repository.BusinessRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class BusinessService {

    private final BusinessRepo businessModelRepository;

    @Autowired
    public BusinessService(BusinessRepo businessModelRepository) {
        this.businessModelRepository = businessModelRepository;
    }

    // Получить все записи
    public List<BusinessModel> findAll() {
        return businessModelRepository.findAll();
    }

    // Получить запись по ID
    public BusinessModel findById(Long id) {
        return businessModelRepository.findById(id).orElse(null);
    }

    // Создать новую запись
    public BusinessModel create(BusinessModel businessModel) {
        businessModel.setCreatedAt(OffsetDateTime.now()); // Устанавливаем дату создания
        businessModel.setUpdatedAt(OffsetDateTime.now()); // Устанавливаем дату обновления
        return businessModelRepository.save(businessModel);
    }

    // Обновить существующую запись
    public BusinessModel update(Long id, BusinessModel updatedBusinessModel) {
        if (businessModelRepository.existsById(id)) {
            updatedBusinessModel.setId(id);
            updatedBusinessModel.setUpdatedAt(OffsetDateTime.now()); // Обновляем дату
            return businessModelRepository.save(updatedBusinessModel);
        }
        return null; // Или можно выбросить исключение
    }

    // Удалить запись
    public void delete(Long id) {
        businessModelRepository.deleteById(id);
    }
}

