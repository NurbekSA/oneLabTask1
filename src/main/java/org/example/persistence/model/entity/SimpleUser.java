package org.example.persistence.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SimpleUser {
    @Id
    long id;
    String username;
    String password;
    private Set<RoleModel> roles = new HashSet<>();

}
