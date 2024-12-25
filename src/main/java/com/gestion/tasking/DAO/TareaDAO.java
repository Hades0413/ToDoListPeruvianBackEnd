package com.gestion.tasking.DAO;

import java.util.List;

import com.gestion.tasking.entity.Tarea;


public interface TareaDAO {
    Tarea registrarTarea(int idProyecto, int idUsuario, String nombre, String descripcion, 
                         Integer prioridad, Integer estado, String fechaVencimiento) throws Exception;
    
    
    List<Tarea> obtenerTareasPorProyecto(int idProyecto);




    Tarea actualizarTarea(int idTarea, String nombre, String descripcion, Integer prioridad, Integer estado,
			String fechaVencimiento) throws Exception;


    
    
    


    
	void eliminarTarea(int idTarea) throws Exception;
	

	boolean existeTarea(int idTarea) throws Exception;


	Tarea obtenerTareaPorId(int idTarea) throws Exception;

    
    
}

