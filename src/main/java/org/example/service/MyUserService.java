//package org.example.service;
//
//
//import org.example.model.MyUser;
//import org.example.repository.MyUserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import java.util.Optional;
//
//@Service
//public class MyUserService {
//
//    @Autowired
//    private MyUserRepo userRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    public MyUser createUser(String login, String password, String role) {
//        String encodedPassword = passwordEncoder.encode(password);
//        MyUser user = new MyUser(null, login, encodedPassword, role);
//        return userRepository.save(user);
//    }
//
//    public MyUser updateUser(Long id, String login, String password) {
//        Optional<MyUser> userOptional = userRepository.findById(id);
//        if (userOptional.isPresent()) {
//            MyUser user = userOptional.get();
//            user.setLogin(login);
//            user.setPassword(passwordEncoder.encode(password));
//            return userRepository.save(user);
//        }
//        throw new RuntimeException("User not found with id " + id);
//    }
//
//    public boolean checkLoginAndPassword(String login, String password) {
//        Optional<MyUser> userOptional = userRepository.findByLogin(login);
//        if (userOptional.isPresent()) {
//            MyUser user = userOptional.get();
//            user.checkPassword(password, passwordEncoder);
//            return user.checkPassword(password, passwordEncoder);
//        }
//        return false;
//    }
//}