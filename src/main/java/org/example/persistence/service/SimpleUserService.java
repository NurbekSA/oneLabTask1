package org.example.persistence.service;

import lombok.RequiredArgsConstructor;
import org.example.persistence.model.dto.RegistrationRequestDTO;
import org.example.persistence.model.entity.RoleModel;
import org.example.persistence.model.entity.SimpleUser;
import org.example.persistence.repository.RoleRepo;
import org.example.persistence.repository.SimpleUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimpleUserService {
    @Autowired
    private SimpleUserRepo userRepository;

    @Autowired
    private RoleRepo roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(RegistrationRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        SimpleUser user = new SimpleUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<RoleModel> roles = request.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found")))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        userRepository.save(user);
    }
}
