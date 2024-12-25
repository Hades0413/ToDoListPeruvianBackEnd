package com.gestion.tasking.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.tasking.entity.Prioridad;
import com.gestion.tasking.entity.Proyecto;
import com.gestion.tasking.entity.User;
import com.gestion.tasking.repository.UserRepository;
import com.gestion.tasking.service.PrioridadService;
import com.gestion.tasking.service.ProyectoService;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private PrioridadService prioridadService;
    
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProyectoController.class);

    @PostMapping("/user")
    public ResponseEntity<?> obtenerProyectosPorUsuario(@RequestBody Map<String, Integer> request) {
        Integer usuarioId = request.get("Id");
        if (usuarioId == null) {
            logger.warn("No se proporcionó un ID de usuario en la solicitud");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "El ID de usuario es requerido"));
        }

        Optional<User> optionalUsuario = userRepository.findById(usuarioId);

        if (optionalUsuario.isEmpty()) {
            logger.warn("El usuario con ID {} no existe", usuarioId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "El usuario que se intenta buscar no existe"));
        }

        User usuario = optionalUsuario.get();
        List<Proyecto> proyectos = proyectoService.listarProyectosPorUsuario(usuario);

        if (proyectos.isEmpty()) {
            logger.info("El usuario con ID {} no tiene proyectos activos", usuarioId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Este usuario no tiene proyectos activos"));
        }

        // Crear la respuesta con los datos necesarios y omitir detalles del usuario
        List<Map<String, Object>> proyectosResponse = proyectos.stream().map(proyecto -> {
            // Usar LinkedHashMap para mantener el orden de los campos
            Map<String, Object> proyectoMap = new LinkedHashMap<>();
            proyectoMap.put("idProyecto", proyecto.getIdTgProyectos());
            proyectoMap.put("nombreProyecto", proyecto.getNombreTgProyectos());
            proyectoMap.put("descripcionProyecto", proyecto.getDescripcionTgProyectos());
            proyectoMap.put("idPrioridad", proyecto.getPrioridad().getIdPrioridad());

            // Formatear la fecha de creación para incluir la hora correctamente
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String fechaCreacionFormatted = proyecto.getFechaCreacionTgProyectos().format(formatter);
            proyectoMap.put("fechaCreacion", fechaCreacionFormatted);

            proyectoMap.put("fechaVencimiento", proyecto.getFechaVencimientoTgProyectos());

            return proyectoMap;
        }).collect(Collectors.toList());

        logger.info("Proyectos encontrados para el usuario con ID {}: {}", usuarioId, proyectos.size());
        return ResponseEntity.ok(proyectosResponse);
    }



    
    
    
    @GetMapping("/{idProyecto}")
    public ResponseEntity<?> listarProyectoPorId(@PathVariable(value = "idProyecto", required = false) Integer idProyecto) {
        // Validación: si no se proporciona idProyecto
        if (idProyecto == null || idProyecto <= 0) {
            logger.warn("Falta el ID del proyecto en la solicitud");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Falta el ID del proyecto. Se debe introducir un ID de proyecto válido en la URL."));
        }

        Optional<Proyecto> optionalProyecto = proyectoService.findById(idProyecto);

        if (optionalProyecto.isEmpty()) {
            logger.warn("El proyecto con ID {} no existe", idProyecto);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "El proyecto con ID " + idProyecto + " no existe"));
        }

        Proyecto proyecto = optionalProyecto.get();
        logger.info("Se encontró el proyecto con ID {}", idProyecto);

        // Mapear el proyecto a un HashMap para la salida
        Map<String, Object> response = Map.of(
            "idUsuario", proyecto.getUsuario() != null ? proyecto.getUsuario().getId() : "No disponible",
            "nombre", proyecto.getNombreTgProyectos(),
            "descripcion", proyecto.getDescripcionTgProyectos(),
            "prioridad", proyecto.getPrioridad() != null ? proyecto.getPrioridad().getNombrePrioridad() : "No definida",
            "fechaCreacion", proyecto.getFechaCreacionTgProyectos(),
            "fechaVencimiento", proyecto.getFechaVencimientoTgProyectos()
        );

        return ResponseEntity.ok(response);
    }


    
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarProyecto(@RequestBody Map<String, Object> input) {
        try {
            // Log para verificar el contenido de la entrada
            System.out.println("Contenido de la solicitud: " + input);

            // Crear una nueva instancia de Proyecto
            Proyecto proyecto = new Proyecto();

            // Mapear los valores desde la entrada según el nuevo formato
            Integer idUsuario = (Integer) input.get("idUsuario");
            if (idUsuario == null || idUsuario <= 0) {
                return ResponseEntity.badRequest().body("El ID del usuario es requerido.");
            }
            User usuario = new User();
            usuario.setId(idUsuario);
            proyecto.setUsuario(usuario);

            proyecto.setNombreTgProyectos((String) input.get("nombreProyecto"));
            proyecto.setDescripcionTgProyectos((String) input.get("descripcionProyecto"));

            // Generar automáticamente la fecha de creación con la hora actual si no se envía en el JSON
            LocalDateTime fechaCreacion = LocalDateTime.now(); // Usa la fecha y hora actual
            proyecto.setFechaCreacionTgProyectos(fechaCreacion);

            // Convertir la fechaVencimiento
            String fechaVencimientoString = (String) input.get("fechaVencimiento");
            if (fechaVencimientoString == null) {
                return ResponseEntity.badRequest().body("La fecha de vencimiento es requerida.");
            }
            try {
                LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoString);
                proyecto.setFechaVencimientoTgProyectos(fechaVencimiento);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Formato de fecha de vencimiento inválido.");
            }

            // La prioridad
            Integer idPrioridad = (Integer) input.get("prioridad");
            if (idPrioridad == null || idPrioridad <= 0) {
                return ResponseEntity.badRequest().body("El ID de la prioridad es requerido.");
            }
            Prioridad prioridad = new Prioridad();
            prioridad.setIdPrioridad(idPrioridad);
            proyecto.setPrioridad(prioridad);

            // Registrar el proyecto
            Proyecto nuevoProyecto = proyectoService.agregarProyecto(proyecto);

            // Crear la respuesta en el formato deseado usando LinkedHashMap
            Map<String, Object> response = new LinkedHashMap<>();  // Mantener ordenado el formato de salida
            response.put("idUsuario", nuevoProyecto.getUsuario().getId());
            response.put("idProyecto", nuevoProyecto.getIdTgProyectos());
            response.put("nombreProyecto", nuevoProyecto.getNombreTgProyectos());
            response.put("descripcionProyecto", nuevoProyecto.getDescripcionTgProyectos());
            response.put("idPrioridad", nuevoProyecto.getPrioridad().getIdPrioridad());

            // Formatear la fechaCreacion para que tenga la hora correcta
            DateTimeFormatter responseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaCreacionFormatted = nuevoProyecto.getFechaCreacionTgProyectos().format(responseFormatter);
            response.put("fechaCreacion", fechaCreacionFormatted);

            // Incluir la fechaVencimiento en la respuesta
            response.put("fechaVencimiento", nuevoProyecto.getFechaVencimientoTgProyectos());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Mostrar el mensaje de error específico en los logs
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar el proyecto: " + e.getMessage());
        }
    }



    
}
