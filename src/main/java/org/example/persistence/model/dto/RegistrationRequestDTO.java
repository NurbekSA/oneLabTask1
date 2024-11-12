package org.example.persistence.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Set;

@Getter
@Setter
public class RegistrationRequestDTO {
    private String username;
    private String password;
    private Set<String> roles;
}
