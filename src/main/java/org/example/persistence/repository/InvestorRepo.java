package org.example.persistence.repository;

import org.example.persistence.model.entity.InvestorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepo extends JpaRepository<InvestorModel, Long> {
    InvestorModel findByIin(String iin);

}

