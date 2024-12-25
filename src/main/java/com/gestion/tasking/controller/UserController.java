package com.gestion.tasking.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gestion.tasking.DAO.UserDAO;
import com.gestion.tasking.entity.User;

@RestController
@RequestMapping("/api/user")  
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @PostMapping("/buscar")
    public Optional<User> buscarUsuario(@RequestBody Map<String, String> parametros) {
        String username = parametros.get("username");
        String email = parametros.get("email");

        if ((username == null || username.isEmpty()) && (email == null || email.isEmpty())) {
            throw new IllegalArgumentException("Debe proporcionar al menos 'username' o 'email' para realizar la búsqueda.");
        }

        return userDAO.buscarUsuarioPorUsernameOCorreo(username, email);
    }
}
