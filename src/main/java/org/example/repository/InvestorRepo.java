package org.example.repository;

import org.example.model.InvestorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepo extends JpaRepository<InvestorModel, Long> {
    // Добавьте дополнительные методы поиска, если это необходимо
    InvestorModel findByIin(String iin);
}

