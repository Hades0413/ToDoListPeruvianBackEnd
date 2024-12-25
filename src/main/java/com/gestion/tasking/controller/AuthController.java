package com.gestion.tasking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gestion.tasking.entity.User;
import com.gestion.tasking.model.AuthResponse;
import com.gestion.tasking.model.LoginRequest;
import com.gestion.tasking.service.AuthService;
import com.gestion.tasking.service.UserService;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // Verificar si el username ya existe
            if (userService.existsByUsername(user.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username ya existe");
            }

            // Verificar si el DNI ya existe
            if (userService.existsByDni(user.getDni())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("DNI ya existe");
            }

            // Verificar si el email ya existe
            if (userService.existsByEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email ya existe");
            }

            // Registrar el nuevo usuario
            User newUser = userService.registerUser(
                user.getNombre(),
                user.getApellido(),
                user.getDni(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
            );

            // Retornar el usuario registrado con la fecha formateada
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        // Validar que el email o username no esté vacío o nulo
        if ((loginRequest.getEmail() == null || loginRequest.getEmail().isEmpty()) &&
            (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(new AuthResponse(400, "El correo electrónico o username es obligatorio"));
        }

        // Validar que la contraseña no esté vacía o nula
        else if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(new AuthResponse(400, "La contraseña es obligatoria"));
        }

        // Validación adicional de la contraseña (puedes agregar reglas más estrictas si lo deseas)
        else if (loginRequest.getPassword().length() < 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(new AuthResponse(400, "La contraseña debe tener al menos 8 caracteres"));
        }

        // Autenticación del usuario por email o username
        User user = null;

        if (loginRequest.getEmail() != null && !loginRequest.getEmail().isEmpty()) {
            // Intentar autenticación por email
            user = authService.authenticateUserByEmail(loginRequest.getEmail(), loginRequest.getPassword());
        } else if (loginRequest.getUsername() != null && !loginRequest.getUsername().isEmpty()) {
            // Intentar autenticación por username
            user = authService.authenticateUserByUsername(loginRequest.getUsername(), loginRequest.getPassword());
        }

        // Si no se encuentra el usuario o las credenciales son incorrectas
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(new AuthResponse(401, "Credenciales incorrectas"));
        }

        // Si todo es correcto, retornar el mensaje de éxito
        return ResponseEntity.ok(new AuthResponse(200, "Login exitoso"));
    }


}
