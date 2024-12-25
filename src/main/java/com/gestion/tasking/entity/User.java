package com.gestion.tasking.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tm_usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int id;

    @Column(name = "nombre_usuario")
    private String nombre;

    @Column(name = "apellido_usuario")
    private String apellido;

    @Column(name = "dni_usuario")
    private String dni;

    @Column(name = "username_usuario")
    private String username;

    @Column(name = "email_usuario")
    private String email;

    @Column(name = "password_usuario")
    private String password;

    @Column(name = "fecha_creacion_usuario")
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }

    @JsonFormat(pattern = "dd/MM/yyyy") 
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
}
