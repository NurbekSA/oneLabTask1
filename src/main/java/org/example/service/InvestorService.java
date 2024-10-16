package org.example.service;

import org.example.model.InvestorModel;
import org.example.repository.InvestorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class InvestorService {

    private final InvestorRepo investorRepo;

    @Autowired
    public InvestorService(InvestorRepo investorRepo) {
        this.investorRepo = investorRepo;
    }

    // Получить все записи
    public List<InvestorModel> findAll() {
        return investorRepo.findAll();
    }

    // Получить запись по ID
    public InvestorModel findById(Long id) {
        return investorRepo.findById(id).orElse(null);
    }

    // Создать новую запись
    public InvestorModel create(InvestorModel investorModel) {
        investorModel.setCreatedAt(OffsetDateTime.now()); // Устанавливаем дату создания
        investorModel.setUpdatedAt(OffsetDateTime.now()); // Устанавливаем дату обновления
        return investorRepo.save(investorModel);
    }

    // Обновить существующую запись
    public InvestorModel update(Long id, InvestorModel updatedInvestorModel) {
        if (investorRepo.existsById(id)) {
            updatedInvestorModel.setId(id);
            updatedInvestorModel.setUpdatedAt(OffsetDateTime.now()); // Обновляем дату
            return investorRepo.save(updatedInvestorModel);
        }
        return null; // Или можно выбросить исключение
    }

    // Удалить запись
    public void delete(Long id) {
        investorRepo.deleteById(id);
    }
}
