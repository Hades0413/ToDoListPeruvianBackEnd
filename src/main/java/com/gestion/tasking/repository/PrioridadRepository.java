package com.gestion.tasking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.tasking.entity.Prioridad;

@Repository
public interface PrioridadRepository extends JpaRepository<Prioridad, Integer> {
    Prioridad findByNombrePrioridad(String nombrePrioridad);
}
