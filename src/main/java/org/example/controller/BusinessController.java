package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.persistence.model.dto.BusinessModelDTO;
import org.example.persistence.service.BusinessService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;

    @GetMapping
    public List<BusinessModelDTO> getAll() {
        return businessService.findAll();
    }

    @GetMapping("/{id}")
    public BusinessModelDTO getById(@PathVariable Long id) {
        return businessService.findById(id);
    }

    @PostMapping
    public BusinessModelDTO create(@RequestBody BusinessModelDTO businessModel) {
        return businessService.create(businessModel);
    }
}
