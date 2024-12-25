package com.gestion.tasking.entity;

import java.util.Date;  // Cambiar a java.util.Date

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@NamedStoredProcedureQuery(
    name = "sp_insertar_proyecto",
    procedureName = "sp_insertar_proyecto",
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id_usuario", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id_tm_prioridad", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_nombre_tg_proyectos", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_descripcion_tg_proyectos", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_fecha_vencimiento", type = Date.class),  // Cambiar a java.util.Date
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_mensaje", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_codigo", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_id_tg_proyectos", type = Integer.class)
    }
)
@AllArgsConstructor
@Data
public class ProyectoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String descripcion;

    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento; 
}
