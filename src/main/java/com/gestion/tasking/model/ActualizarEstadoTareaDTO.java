package com.gestion.tasking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar el estado de una tarea.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor // Agregado para permitir la deserialización con Jackson
public class ActualizarEstadoTareaDTO {

    private int idTgTareas;
    private int nuevoEstado;
}
