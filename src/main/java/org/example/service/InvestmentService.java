package org.example.service;

import org.example.model.InvestmentModel;
import org.example.repository.InvestmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class InvestmentService {

    private final InvestmentRepo investmentRepo;

    @Autowired
    public InvestmentService(InvestmentRepo investmentRepo) {
        this.investmentRepo = investmentRepo;
    }

    // Получить все записи
    public List<InvestmentModel> findAll() {
        return investmentRepo.findAll();
    }

    // Получить запись по ID
    public InvestmentModel findById(Long id) {
        return investmentRepo.findById(id).orElse(null);
    }

    // Создать новую запись
    public InvestmentModel create(InvestmentModel investmentModel) {
        investmentModel.setInvestmentDate(OffsetDateTime.now()); // Устанавливаем дату инвестиции
        return investmentRepo.save(investmentModel);
    }

    // Обновить существующую запись
    public InvestmentModel update(Long id, InvestmentModel updatedInvestmentModel) {
        if (investmentRepo.existsById(id)) {
            updatedInvestmentModel.setId(id);
            return investmentRepo.save(updatedInvestmentModel);
        }
        return null; // Или можно выбросить исключение
    }

    // Удалить запись
    public void delete(Long id) {
        investmentRepo.deleteById(id);
    }
}

