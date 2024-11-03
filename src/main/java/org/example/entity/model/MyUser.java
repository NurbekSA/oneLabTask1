//package org.example.model;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@Entity
//@Setter
//@AllArgsConstructor
//public class MyUser {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    Long id;
//    private String login;
//    private String password;
//    private String role;
//    public boolean checkPassword(String rawPassword, BCryptPasswordEncoder encoder) {
//        return encoder.matches(rawPassword, this.password);
//    }
//
//
//    public String getRole() {
//        return role;
//    }
//
//    public String getLogin() {
//        return login;
//    }
//    public long getId(){
//        return this.id;
//    }
//}
