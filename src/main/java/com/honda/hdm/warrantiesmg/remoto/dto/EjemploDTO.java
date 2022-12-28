package com.honda.hdm.warrantiesmg.remoto.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Modelo de datos para otro recurso externo, puede solo agregar los atributos que desea.
 * @author ${user}
 * @since ${date}
 */
@Getter
@Setter
public class EjemploDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private Integer id;
	private String campoString;
}
