package org.example.entity.repository;


import org.example.entity.model.BusinessModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepo extends JpaRepository<BusinessModel, Long> {
    BusinessModel findByCompanyBIN(String companyBIN);
}
