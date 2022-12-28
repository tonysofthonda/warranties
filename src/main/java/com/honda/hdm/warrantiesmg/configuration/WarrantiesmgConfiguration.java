package com.honda.hdm.warrantiesmg.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * En este archivo se crean los beans requeridos para el proyecto
 *    
 * @author ${user}
 * @since ${date}
 */
@Configuration
public class WarrantiesmgConfiguration {

	
	/**
	 * Bean en el cual se especifica el basePackage de descubridor de endpoints para
	 * swagger, se modifica de esta forma ya que en automatico detecta desde el
	 * paquete base, lo cual añade endpoints definidos en spring security por
	 * defecto
	 * 
	 * @author VJC80553
	 * @return Docket
	 * @since 2020-12-30
	 */
	@Bean
	public Docket awesomeApi() {
		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.honda.hdm.warrantiesmg.app.web.controller")).build();
	}
	

}
