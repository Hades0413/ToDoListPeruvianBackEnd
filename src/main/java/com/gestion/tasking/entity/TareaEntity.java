package com.gestion.tasking.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tg_tareas")
public class TareaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tg_tareas")
    private int idTgTareas;

    @Column(name = "id_tg_proyectos", nullable = false)
    private int idTgProyectos;

    @Column(name = "nombre_tg_tareas", unique = true, nullable = false)
    private String nombreTgTareas;

    @Column(name = "descripcion_tg_tareas")
    private String descripcionTgTareas;

    @Column(name = "id_tm_prioridad", nullable = false)
    private Integer idTmPrioridad = 1;

    @Column(name = "id_tm_estado", nullable = false)
    private Integer idTmEstado = 1;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_vencimiento_tg_tareas")
    private LocalDate fechaVencimientoTgTareas;

    @Column(name = "fecha_creacion_tg_tareas", updatable = false)
    private LocalDateTime fechaCreacionTgTareas;
}
