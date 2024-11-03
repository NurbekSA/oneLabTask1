package org.example.entity.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.model.exception.ResourceNotFoundException;
import org.example.entity.model.BusinessModel;
import org.example.entity.repository.BusinessRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepo businessRepo;

    public List<BusinessModel> findAll() {
        List<BusinessModel> businesses = businessRepo.findAll();
        if (businesses.isEmpty()) {
            throw new ResourceNotFoundException("No business records found");
        }
        return businesses;
    }

    public BusinessModel findById(Long id) {
        return businessRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Business with id " + id + " not found"));
    }

    @Transactional
    public BusinessModel create(BusinessModel businessModel) {
        if (businessModel == null) {
            throw new IllegalArgumentException("Business model cannot be null");
        }
        businessModel.setCreatedAt(System.currentTimeMillis());
        businessModel.setUpdatedAt(System.currentTimeMillis());
        return businessRepo.save(businessModel);
    }
}
