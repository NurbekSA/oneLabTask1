package org.example.persistence.repository;


import org.example.persistence.model.entity.BusinessModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepo extends JpaRepository<BusinessModel, Long> {
    BusinessModel findByCompanyBIN(String companyBIN);
}
