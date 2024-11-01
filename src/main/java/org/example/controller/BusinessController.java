package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.BusinessModel;
import org.example.service.BusinessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("business")
@AllArgsConstructor
public class BusinessController {
    private final BusinessService businessService;

    @GetMapping
    public List<BusinessModel> getAll(){
        ResponseEntity<?> response = businessService.findAll();
        if(response.getStatusCode() == HttpStatus.OK){
            return (List<BusinessModel>) response.getBody();
        }
        return null;
    }

    @GetMapping(value = "/{id}")
    public BusinessModel getById(@PathVariable Long id){
        ResponseEntity<?> response = businessService.findById(id);
        if(response.getStatusCode() == HttpStatus.OK){
            return (BusinessModel) response.getBody();
        }
        return null;
    }

    @PostMapping
    public HttpStatusCode create(@RequestBody BusinessModel businessModel){
        ResponseEntity<?> response = businessService.create(businessModel);
        return response.getStatusCode();
    }

}
