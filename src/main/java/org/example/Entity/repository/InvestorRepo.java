package org.example.entity.repository;

import org.example.entity.model.InvestorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepo extends JpaRepository<InvestorModel, Long> {
    InvestorModel findByIin(String iin);

}

