package com.gestion.tasking.DAO;

import java.util.List;
import java.util.Optional;

import com.gestion.tasking.entity.Proyecto;
import com.gestion.tasking.entity.User;


public interface ProyectoDAO {
    List<Proyecto> findByUsuario(User usuario);
    List<Proyecto> listarProyectosPorUsuario(Integer idUsuario);
    Optional<Proyecto> findById(Integer idProyecto);

    Proyecto save(Proyecto proyecto);
}



