package com.honda.hdm.warrantiesmg.util;


/**
 * Enunmerador de errores para el m&oacute;dulo de Ejemplo.
 * Se recomienda manterner en esta clase todos los erroes que genera el módulo.
 *
 * @author ${user} 
 * @since ${date}
 */
public enum EjemploError {
	ERROR_DE_EJEMPLO("Sucedió un error en el módulo de ejemplo");

	private EjemploError(String message) {
		this.message = message;
	}

	private String message;

	public String getMessage() {
		return message;
	}
}