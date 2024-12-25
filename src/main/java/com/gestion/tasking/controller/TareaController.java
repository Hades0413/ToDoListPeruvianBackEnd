package com.gestion.tasking.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.tasking.entity.Tarea;
import com.gestion.tasking.entity.TareaEntity;
import com.gestion.tasking.model.ActualizarEstadoTareaDTO;
import com.gestion.tasking.model.ActualizarPrioridadTareaDTO;
import com.gestion.tasking.model.AuthResponse;
import com.gestion.tasking.service.TareaService;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarTarea(@RequestBody Tarea tarea) {
        try {
            // Validaciones
            if (tarea.getIdProyecto() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse(400, "El ID del proyecto es obligatorio y debe ser mayor a cero."));
            }
            if (tarea.getNombre() == null || tarea.getNombre().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse(400, "El nombre de la tarea es obligatorio."));
            }
            if (tarea.getDescripcion() == null || tarea.getDescripcion().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse(400, "La descripción de la tarea es obligatoria."));
            }
            if (tarea.getPrioridad() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse(400, "La prioridad de la tarea es obligatoria."));
            }
            if (tarea.getEstado() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse(400, "El estado de la tarea es obligatorio."));
            }
            if (tarea.getFechaVencimiento() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse(400, "La fecha de vencimiento es obligatoria."));
            }

            // Llamar al servicio para registrar la tarea, ahora incluyendo el idUsuario
            Tarea nuevaTarea = tareaService.registrarTarea(
                tarea.getIdProyecto(),
                tarea.getIdUsuario(), // Asegúrate de que `idUsuario` esté presente en el objeto Tarea
                tarea.getNombre(),
                tarea.getDescripcion(),
                tarea.getPrioridad(),
                tarea.getEstado(),
                tarea.getFechaVencimiento()
            );

            // Retornar la tarea registrada
            return ResponseEntity.ok(nuevaTarea);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PostMapping("/proyecto")
    public ResponseEntity<?> obtenerTareasPorProyecto(@RequestBody Map<String, Integer> request) {
        Integer idProyecto = request.get("idProyecto");
        if (idProyecto == null || idProyecto <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse(400, "El ID del proyecto debe ser mayor a cero."));
        }

        List<Tarea> tareas = tareaService.obtenerTareasPorProyecto(idProyecto);

        if (tareas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new AuthResponse(404, "No se encontraron tareas para este proyecto."));
        }

        // Excluir campo idProyecto
        List<Map<String, Object>> tareasResponse = new ArrayList<>();
        for (Tarea tarea : tareas) {
            Map<String, Object> tareaData = new HashMap<>();
            tareaData.put("idTarea", tarea.getIdTarea());
            tareaData.put("nombre", tarea.getNombre());
            tareaData.put("descripcion", tarea.getDescripcion());
            tareaData.put("prioridad", tarea.getPrioridad());
            tareaData.put("estado", tarea.getEstado());
            tareaData.put("fechaVencimiento", tarea.getFechaVencimiento());
            tareaData.put("fechaCreacion", tarea.getFechaCreacion());
            tareasResponse.add(tareaData);
        }

        return ResponseEntity.ok(tareasResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTarea(@PathVariable("id") int id, @RequestBody Tarea tarea) {
        try {
            // Verificar si la tarea existe
            if (!tareaService.existeTarea(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new AuthResponse(400, "La tarea con el ID " + id + " no existe."));
            }

            // Validaciones de los campos obligatorios
            if (tarea.getNombre() == null || tarea.getNombre().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse(400, "El nombre de la tarea es obligatorio."));
            }
            if (tarea.getDescripcion() == null || tarea.getDescripcion().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse(400, "La descripción de la tarea es obligatoria."));
            }
            if (tarea.getPrioridad() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse(400, "La prioridad de la tarea es obligatoria."));
            }
            if (tarea.getEstado() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse(400, "El estado de la tarea es obligatorio."));
            }
            if (tarea.getFechaVencimiento() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse(400, "La fecha de vencimiento es obligatoria."));
            }

            // Actualización de la tarea
            Tarea actualizarTarea = tareaService.actualizarTarea(
                    id,
                    tarea.getNombre(),
                    tarea.getDescripcion(),
                    tarea.getPrioridad(),
                    tarea.getEstado(),
                    tarea.getFechaVencimiento()
            );

            // Respuesta de éxito
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new AuthResponse(200, "Actualización exitosa"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponse(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponse(500, "Error en el servidor"));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<AuthResponse> eliminarTarea(@PathVariable int id) {
        try {
            if (!tareaService.existeTarea(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new AuthResponse(400, "La tarea con el ID " + id + " no existe."));
            }
            tareaService.eliminarTarea(id);
            return ResponseEntity.ok(new AuthResponse(200, "Tarea con ID " + id + " eliminada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse(500, "Ocurrió un error al eliminar la tarea"));
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<AuthResponse> handleJsonParseException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new AuthResponse(400, "Hay errores en la validación, revisa que los datos en el JSON sean correctos."));
    }
    
    
    
    @PostMapping("/actualizar-estado")
    public TareaEntity actualizarEstado(@RequestBody  ActualizarEstadoTareaDTO request) {
    	
    	return tareaService.actualizarEstadoTarea(request.getIdTgTareas(), request.getNuevoEstado());
    	
    }
    
    
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTareaPorId(@PathVariable("id") int id) {
        try {
            Tarea tarea = tareaService.obtenerTareaPorId(id);
            return ResponseEntity.ok(tarea);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new AuthResponse(404, "No se encontró la tarea con ID: " + id));
        }
    }
    
    
    
    @PostMapping("/actualizar-prioridad")
    public ResponseEntity<TareaEntity> actualizarPrioridad(@RequestBody ActualizarPrioridadTareaDTO prioridadDTO) {
        TareaEntity tareaActualizada = tareaService.actualizarPrioridadTarea(
                prioridadDTO.getIdTgTareas(),
                prioridadDTO.getIdTmPrioridad()
        );
        return ResponseEntity.ok(tareaActualizada);
    }

    
    
}
