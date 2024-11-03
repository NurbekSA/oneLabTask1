package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.model.BusinessModel;
import org.example.entity.service.BusinessService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;

    @GetMapping
    public List<BusinessModel> getAll() {
        return businessService.findAll();
    }

    @GetMapping("/{id}")
    public BusinessModel getById(@PathVariable Long id) {
        return businessService.findById(id);
    }

    @PostMapping
    public BusinessModel create(@RequestBody BusinessModel businessModel) {
        return businessService.create(businessModel);
    }
}
