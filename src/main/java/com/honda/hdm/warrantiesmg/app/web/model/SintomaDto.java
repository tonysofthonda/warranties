package com.honda.hdm.warrantiesmg.app.web.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@ApiModel(description = "Modelo para el servicio de Catalogo Sitomas ")
public class SintomaDto {
	
	@ApiModelProperty(value = "Campo que sirve para almaceanar la llave primaria de la tabla")
	private Long id;
	
	@ApiModelProperty(value = "Campo que sirve para almaceanar el codigo del sintoma")
	private String code;
		
	@ApiModelProperty(value = "Campo que sirve para almaceanar la descripcion en ingles")
	private String descriptionEng;
	
	@ApiModelProperty(value = "Campo que sirve para almaceanar la descripcion en españoñ")
	private String descriptionSpa;
	
	@ApiModelProperty(value = "Campo que sirve para almaceanar el estatus del registro de sintomas")
	private Boolean status;
}
