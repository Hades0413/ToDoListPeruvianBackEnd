package com.gestion.tasking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.tasking.DAO.ProyectoDAO;
import com.gestion.tasking.entity.Proyecto;
import com.gestion.tasking.entity.User;
import com.gestion.tasking.model.ResponseDTO;
import jakarta.persistence.EntityManager;

import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoDAO proyectoDAO;
    
    @Autowired
    private EntityManager entityManager;

   
    public List<Proyecto> listarProyectosPorUsuario(User usuario) {
        return proyectoDAO.findByUsuario(usuario);
    }
    
    
    @Transactional

    public Proyecto agregarProyecto(Proyecto proyecto) {
        // Lógica adicional, como validar datos del proyecto si es necesario
        return proyectoDAO.save(proyecto);
    }

    
    
    public List<Proyecto> listarProyectosPorUsuario(Integer idUsuario) {
        return proyectoDAO.listarProyectosPorUsuario(idUsuario);
    }
    
    public Optional<Proyecto> findById(Integer idProyecto) {
        return proyectoDAO.findById(idProyecto);
    }

    
    
    
    
    
    

public ResponseDTO insertarProyecto(Integer idUsuario, Integer idPrioridad, String nombreProyecto, String descripcionProyecto, java.sql.Date fechaVencimiento) {
        
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("sp_insertar_proyecto");

        
        query.setParameter("p_id_usuario", idUsuario);
        query.setParameter("p_id_tm_prioridad", idPrioridad);
        query.setParameter("p_nombre_tg_proyectos", nombreProyecto);
        query.setParameter("p_descripcion_tg_proyectos", descripcionProyecto);
        query.setParameter("p_fecha_vencimiento", fechaVencimiento);

        
        query.execute();

        //PARA LA RESPUSTA DEL CONTROLER :D
        String mensaje = (String) query.getOutputParameterValue("p_mensaje");
        Integer codigo = (Integer) query.getOutputParameterValue("p_codigo");
        Integer idProyecto = (Integer) query.getOutputParameterValue("p_id_tg_proyectos");

     
        return new ResponseDTO(mensaje, codigo, idProyecto);
    }
    
}
