package com.gestion.tasking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarPrioridadTareaDTO {

    private int idTgTareas;
    private int idTmPrioridad;
}
