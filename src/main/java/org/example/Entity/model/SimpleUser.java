package org.example.entity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SimpleUser {
    @Id
    long id;
    String username;
    String password;
    String authority;
}
