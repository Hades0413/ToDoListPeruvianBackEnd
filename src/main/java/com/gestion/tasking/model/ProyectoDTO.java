package com.gestion.tasking.model;



import java.util.Date;

import lombok.Data;


@Data
public class ProyectoDTO {
    private Integer idUsuario;
    private Integer idPrioridad;
    private String nombreProyecto;
    private String descripcionProyecto;
    private Date fechaVencimiento;

}
