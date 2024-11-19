package org.example.persistence.service;

import lombok.RequiredArgsConstructor;
import org.example.persistence.model.dto.BusinessModelDTO;
import org.example.persistence.model.entity.BusinessModel;
import org.example.persistence.model.exception.ResourceNotFoundException;
import org.example.persistence.repository.BusinessRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private static final Logger logger = LoggerFactory.getLogger(BusinessService.class);
    private final BusinessRepo businessRepo;

    // Преобразование из BusinessModel в BusinessModelDTO
    private BusinessModelDTO convertToDto(BusinessModel businessModel) {
        return new BusinessModelDTO(
                businessModel.getId(),
                businessModel.getDirectorNumber(),
                businessModel.getDirectorMail(),
                businessModel.getIsCheked(),
                businessModel.getDirectorFIO(),
                businessModel.getDirectorIIN(),
                businessModel.getCompanyName(),
                businessModel.getCompanyBIN(),
                businessModel.getAddress(),
                businessModel.getTypeOfPaymentSystem(),
                businessModel.getSector(),
                businessModel.getDateOfBusinessStarted(),
                businessModel.getCreatedAt(),
                businessModel.getUpdatedAt()
        );
    }

    // Преобразование из BusinessModelDTO в BusinessModel
    private BusinessModel convertToEntity(BusinessModelDTO businessDTO) {
        BusinessModel businessModel = new BusinessModel();
        businessModel.setId(businessDTO.getId());
        businessModel.setDirectorNumber(businessDTO.getDirectorNumber());
        businessModel.setDirectorMail(businessDTO.getDirectorMail());
        businessModel.setIsCheked(businessDTO.getIsCheked());
        businessModel.setDirectorFIO(businessDTO.getDirectorFIO());
        businessModel.setDirectorIIN(businessDTO.getDirectorIIN());
        businessModel.setCompanyName(businessDTO.getCompanyName());
        businessModel.setCompanyBIN(businessDTO.getCompanyBIN());
        businessModel.setAddress(businessDTO.getAddress());
        businessModel.setTypeOfPaymentSystem(businessDTO.getTypeOfPaymentSystem());
        businessModel.setSector(businessDTO.getSector());
        businessModel.setDateOfBusinessStarted(businessDTO.getDateOfBusinessStarted());
        businessModel.setCreatedAt(businessDTO.getCreatedAt());
        businessModel.setUpdatedAt(businessDTO.getUpdatedAt());
        return businessModel;
    }

    // Admin
    public List<BusinessModelDTO> findAll() {
        logger.info("FIND_ALL: Retrieving all business records from the database.");
        List<BusinessModel> businesses = businessRepo.findAll();
        if (businesses.isEmpty()) {
            throw new ResourceNotFoundException("No business records found");
        }
        return businesses.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    public BusinessModelDTO findById(Long id) {
        logger.info("FIND_BY_ID: Searching for business with ID: {}", id);
        return convertToDto(businessRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Business with id " + id + " not found")));
    }

    public BusinessModelDTO create(BusinessModelDTO businessDTO) {
        logger.info("CREATE: Creating a new business record.");
        BusinessModel businessModel = convertToEntity(businessDTO);

        businessModel.setCreatedAt(System.currentTimeMillis());
        businessModel.setUpdatedAt(System.currentTimeMillis());
        businessModel.setIsCheked(false);

        BusinessModel savedBusiness = businessRepo.save(businessModel);
        return convertToDto(savedBusiness);
    }

    @Transactional
    public BusinessModelDTO update(Long id, BusinessModelDTO businessDetailsDTO) {
        logger.info("UPDATE: Updating business with ID {}", id);
        BusinessModel existingBusiness = businessRepo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("UPDATE: Business with ID {} not found.", id);
                    return new ResourceNotFoundException("Business with id " + id + " not found");
                });

        existingBusiness.setDirectorNumber(businessDetailsDTO.getDirectorNumber());
        existingBusiness.setDirectorMail(businessDetailsDTO.getDirectorMail());
        existingBusiness.setDirectorFIO(businessDetailsDTO.getDirectorFIO());
        existingBusiness.setAddress(businessDetailsDTO.getAddress());
        existingBusiness.setTypeOfPaymentSystem(businessDetailsDTO.getTypeOfPaymentSystem());
        existingBusiness.setSector(businessDetailsDTO.getSector());
        existingBusiness.setUpdatedAt(System.currentTimeMillis());

        BusinessModel updatedBusiness = businessRepo.save(existingBusiness);
        return convertToDto(updatedBusiness);
    }

    public void delete(Long id) {
        logger.info("DELETE: Deleting business with ID: {}", id);
        if (!businessRepo.existsById(id)) {
            throw new ResourceNotFoundException("Business with id " + id + " not found");
        }
        businessRepo.deleteById(id);
        logger.info("DELETE: Business with ID {} deleted successfully.", id);
    }
}
