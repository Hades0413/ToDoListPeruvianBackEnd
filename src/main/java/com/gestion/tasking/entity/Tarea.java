package com.gestion.tasking.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tarea {
    private int idProyecto;
    private int idTarea;
    private int idUsuario;
    private String nombre;
    private String descripcion;
    private Integer prioridad;
    private Integer estado;
    
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;

    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaVencimiento;
}
