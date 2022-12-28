package com.honda.hdm.warrantiesmg.app.web.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@ApiModel(description = "Modelo para el servicio de Catalogo Defectos ")
public class DefectosDto {
	
	@ApiModelProperty(value = "   Campo que sirve para almaceanar la llave primaria de la tabla")
	private Long id;
		
	@ApiModelProperty(value = "Campo que sirve para almaceanar la descripcion en ingles")
	private String descriptionEng;
	
	@ApiModelProperty(value = "Campo que sirve para almaceanar la descripcion en español")
	private String descriptionSpa;
	
	@ApiModelProperty(value = "Campo que sirve para almaceanr el estatus del defecto")
	private Boolean status;

}
