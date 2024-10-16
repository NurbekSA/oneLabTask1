package org.example.repository;


import org.example.model.BusinessModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepo extends JpaRepository<BusinessModel, Long> {
    // Вы можете добавить дополнительные методы поиска, если это необходимо
    BusinessModel findByCompanyBIN(String companyBIN);
}
