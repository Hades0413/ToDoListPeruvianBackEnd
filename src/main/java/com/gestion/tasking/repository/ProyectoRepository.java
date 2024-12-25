package com.gestion.tasking.repository;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestion.tasking.entity.ProyectoEntity;
import com.gestion.tasking.model.ResponseDTO;

@Repository
public interface ProyectoRepository extends JpaRepository<ProyectoEntity, Integer> {

    @Procedure(name = "sp_insertar_proyecto")
    ResponseDTO spInsertarProyecto(
        @Param("p_id_usuario") Integer idUsuario,
        @Param("p_id_tm_prioridad") Integer idPrioridad,
        @Param("p_nombre_tg_proyectos") String nombreProyecto,
        @Param("p_descripcion_tg_proyectos") String descripcionProyecto,
        @Param("p_fecha_vencimiento") java.sql.Date fechaVencimiento,
        
        @Param("p_mensaje") String mensaje,  // Parametro de salida
        @Param("p_codigo") Integer codigo,  // Parametro de salida
        @Param("p_id_tg_proyectos") Integer idProyectoGenerado  // Parametro de salida
    );
}
