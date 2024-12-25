package com.gestion.tasking.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.tasking.entity.User;
import com.gestion.tasking.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;


    public UserService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    public User registerUser(String nombre, String apellido, String dni,
                             String username, String email, String password) {

        String encryptedPassword = passwordEncoder.encode(password);
    	
        User user = new User();
        user.setNombre(nombre);
        user.setApellido(apellido);
        user.setDni(dni);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encryptedPassword);
        user.setFechaCreacion(java.time.LocalDateTime.now());

        logger.info("Registering user with DNI: " + dni);
        return userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        logger.info("Checking if username exists: " + username);
        return userRepository.findByUsername(username) != null;
    }

    public boolean existsByEmail(String email) {
        logger.info("Checking if email exists: " + email);
        return userRepository.findByEmail(email) != null;
    }

    public boolean existsByDni(String dni) {
        logger.info("Checking if DNI exists: " + dni);
        return userRepository.findByDni(dni) != null;
    }
}
