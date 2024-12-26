package com.gestion.tasking.model;

import lombok.Data;

@Data
public class ComentarioDTO {

	private Integer idComentario;

	private Integer idTarea;

	private Integer idUsuario;

	private String comentario;

}