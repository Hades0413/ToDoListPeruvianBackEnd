package com.gestion.tasking.entity;

import java.time.LocalDate;
import java.time.LocalDateTime; // Cambiado a LocalDateTime

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tg_proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tg_proyectos")
    @JsonProperty("idProyecto")
    private Integer idTgProyectos;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User usuario;

    @Column(name = "nombre_tg_proyectos")
    @JsonProperty("nombreProyecto")
    private String nombreTgProyectos;

    @Column(name = "descripcion_tg_proyectos")
    @JsonProperty("descripcionProyecto")
    private String descripcionTgProyectos;

    @ManyToOne
    @JoinColumn(name = "id_tm_prioridad")
    @JsonProperty("prioridad")
    private Prioridad prioridad;

    @Column(name = "fecha_vencimiento_tg_proyectos")
    @JsonProperty("fechaVencimiento")
    private LocalDate fechaVencimientoTgProyectos;

    // Cambiado de LocalDate a LocalDateTime para incluir hora
    @Column(name = "fecha_creacion_tg_proyectos")
    @JsonProperty("fechaCreacion")
    private LocalDateTime fechaCreacionTgProyectos; // Actualizado a LocalDateTime
}
