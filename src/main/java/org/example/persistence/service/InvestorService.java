package org.example.persistence.service;

import lombok.RequiredArgsConstructor;
import org.example.persistence.model.dto.InvestorModelDTO;
import org.example.persistence.model.entity.InvestorModel;
import org.example.persistence.model.exception.ResourceNotFoundException;
import org.example.persistence.repository.InvestorRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvestorService {

    private final InvestorRepo investorRepo;
    private final InvestmentService investmentService;

    // Преобразование из InvestorModel в InvestorModelDTO
    private InvestorModelDTO convertToDto(InvestorModel investorModel) {
        return new InvestorModelDTO(
                investorModel.getId(),
                investorModel.getCards().stream().map(card -> card.getId()).collect(Collectors.toList()),
                investorModel.getInvestment().stream().map(investment -> investment.getId()).collect(Collectors.toList()),
                investorModel.getMail(),
                investorModel.getPhoneNumber(),
                investorModel.getIsChecked(),
                investorModel.getScore(),
                investorModel.getIin(),
                investorModel.getFio(),
                investorModel.getAddress(),
                investorModel.getInvestorType(),
                investorModel.getCreatedAt(),
                investorModel.getUpdatedAt()
        );
    }

    // Преобразование из InvestorModelDTO в InvestorModel
    private InvestorModel convertToEntity(InvestorModelDTO investorDTO) {
        InvestorModel investorModel = new InvestorModel();
        investorModel.setId(investorDTO.getId());
        investorModel.setMail(investorDTO.getMail());
        investorModel.setPhoneNumber(investorDTO.getPhoneNumber());
        investorModel.setIsChecked(investorDTO.getIsChecked());
        investorModel.setScore(investorDTO.getScore());
        investorModel.setIin(investorDTO.getIin());
        investorModel.setFio(investorDTO.getFio());
        investorModel.setAddress(investorDTO.getAddress());
        investorModel.setInvestorType(investorDTO.getInvestorType());
        investorModel.setCreatedAt(investorDTO.getCreatedAt());
        investorModel.setUpdatedAt(investorDTO.getUpdatedAt());
        return investorModel;
    }

    public InvestorModelDTO findById(Long id) {
        InvestorModel investorModel = investorRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investor with id " + id + " not found"));
        return convertToDto(investorModel);
    }

    public InvestorModelDTO create(InvestorModelDTO investorDTO) {
        InvestorModel investorModel = convertToEntity(investorDTO);
        investorModel.setIsChecked(true); // При реальном запуске нужно поменять на false
        investorModel.setCreatedAt(System.currentTimeMillis());
        investorModel.setUpdatedAt(System.currentTimeMillis());
        InvestorModel savedInvestor = investorRepo.save(investorModel);
        return convertToDto(savedInvestor);
    }

    public List<InvestorModelDTO> findAll() {
        List<InvestorModel> investors = investorRepo.findAll();
        if (investors.isEmpty()) {
            throw new ResourceNotFoundException("No investors found");
        }
        return investors.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (!investorRepo.existsById(id)) {
            throw new ResourceNotFoundException("Investor with id " + id + " not found");
        }
        investorRepo.deleteById(id);
    }

    public InvestorModelDTO setChecked(Long id) {
        InvestorModel investor = investorRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investor with id " + id + " not found"));
        investor.setIsChecked(true);
        investor.setUpdatedAt(System.currentTimeMillis());
        InvestorModel updatedInvestor = investorRepo.save(investor);
        return convertToDto(updatedInvestor);
    }
}
