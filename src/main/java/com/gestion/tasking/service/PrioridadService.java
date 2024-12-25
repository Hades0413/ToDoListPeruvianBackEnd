package com.gestion.tasking.service;

import com.gestion.tasking.entity.Prioridad;
import com.gestion.tasking.repository.PrioridadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrioridadService {

    @Autowired
    private PrioridadRepository prioridadRepository;

    public Prioridad findByNombre(String nombre) {
        return prioridadRepository.findByNombrePrioridad(nombre);
    }
}
