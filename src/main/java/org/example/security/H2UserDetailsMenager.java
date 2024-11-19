package org.example.security;

import org.example.persistence.model.entity.SimpleUser;
import org.example.persistence.model.exception.ResourceNotFoundException;
import org.example.persistence.repository.SimpleUserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class H2UserDetailsMenager implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(H2UserDetailsMenager.class);
    private final SimpleUserRepo userRepo;

    public H2UserDetailsMenager(SimpleUserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername: Started");
        SimpleUser user = userRepo.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("SipleUser not found"));
        logger.info("loadUserByUsername: Find user with pass {}", user.getPassword());
        return new H2UserDetails(user);
    }
}
