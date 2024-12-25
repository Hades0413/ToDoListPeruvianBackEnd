package com.gestion.tasking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.tasking.entity.TareaEntity;

public interface TareaRepository extends JpaRepository<TareaEntity, Integer> {
	
	 TareaEntity findByIdTgTareas(Integer idTgTareas);

}
