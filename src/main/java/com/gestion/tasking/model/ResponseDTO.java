package com.gestion.tasking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseDTO {
	
	    private String message;
	    private int code;
	    private Integer idProyecto;

}
