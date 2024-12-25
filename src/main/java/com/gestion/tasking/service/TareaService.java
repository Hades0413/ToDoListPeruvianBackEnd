package com.gestion.tasking.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.tasking.DAO.TareaDAO;
import com.gestion.tasking.entity.Tarea;
import com.gestion.tasking.entity.TareaEntity;
import com.gestion.tasking.repository.TareaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TareaService {

    @Autowired
    private TareaDAO tareaDAO;
    
    @Autowired
	private TareaRepository tareaRepository;

    public Tarea registrarTarea(int idProyecto, int idUsuario, String nombre, String descripcion, 
            Integer prioridad, Integer estado, LocalDate fechaVencimiento) {

// Convertir LocalDate a String en el formato adecuado (yyyy-MM-dd)
String fechaFormateada = fechaVencimiento.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

try {
// Registrar la tarea a través del DAO, ahora pasando el idUsuario
return tareaDAO.registrarTarea(idProyecto, idUsuario, nombre, descripcion, prioridad, estado, fechaFormateada);
} catch (Exception e) {
// Si ocurre un error, propagar la excepción con el mensaje adecuado
throw new RuntimeException(e.getMessage()); // Excepción con el mensaje conciso
}
}

    
    public List<Tarea> obtenerTareasPorProyecto(int idProyecto) {
        return tareaDAO.obtenerTareasPorProyecto(idProyecto);
    }




    	public Tarea actualizarTarea(int idTarea, String nombre, String descripcion, Integer prioridad, Integer estado,
			LocalDate fechaVencimiento) throws Exception {
		
        // Convertir LocalDate a String en el formato adecuado (yyyy-MM-dd)
        String fechaFormateada = fechaVencimiento.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        try {
    		return tareaDAO.actualizarTarea(idTarea, nombre, descripcion, prioridad, estado, fechaFormateada);
        } catch (Exception e) {
            // Si ocurre un error, propagar la excepción con el mensaje adecuado
            throw new RuntimeException(e.getMessage()); // Excepción con el mensaje conciso
        }
	}

	public void eliminarTarea(int idTarea) throws Exception {
		tareaDAO.eliminarTarea(idTarea);
	}
	
	
	public boolean existeTarea(int idTarea) throws Exception {
	    return tareaDAO.existeTarea(idTarea);
	}



	
	public Tarea obtenerTareaPorId(int idTarea) throws Exception {
	    return tareaDAO.obtenerTareaPorId(idTarea);
	}


	

	// Metodo para actualizar el estado de la tarea
	public TareaEntity actualizarEstadoTarea(int idTgTareas, int nuevoEstado) {
	    // Buscar la tarea, lanzar excepción si no se encuentra
	    TareaEntity tarea = tareaRepository.findById(idTgTareas)
	        .orElseThrow(() -> new EntityNotFoundException("No se encontró tarea con el idTgTareas: " + idTgTareas));

	    // Actualizar el estado
	    tarea.setIdTmEstado(nuevoEstado);

	    // Guardar los cambios
	    return tareaRepository.save(tarea);
	}
	
	
	
	
	
	
	public TareaEntity actualizarPrioridadTarea(int idTgTareas, int idTmPrioridad) {
        Optional<TareaEntity> tareaOpt = tareaRepository.findById(idTgTareas);
        if(tareaOpt.isPresent()) {
            TareaEntity tarea = tareaOpt.get();
            tarea.setIdTmPrioridad(idTmPrioridad);
            return tareaRepository.save(tarea);
        }
        else {
            throw new RuntimeException("No se encontro tarea con el idTgTareas:  " + idTgTareas);
        }
    }

    
}
