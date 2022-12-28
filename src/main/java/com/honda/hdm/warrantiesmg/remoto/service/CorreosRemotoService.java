//package com.honda.hdm.warrantiesmg.remoto.service;
//
//
//import org.springframework.stereotype.Service;
//
//import com.honda.hdm.warrantiesmg.remoto.dto.EjemploDTO;
//import com.honda.hdm.warrantiesmg.remoto.repository.CorreosRemotoRepository;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.honda.hdm.hdmbase.app.aspect.RemoteCall;
//import com.honda.hdm.hdmbase.app.aspect.RemoteCallAspect;
//import com.honda.hdm.hdmbase.app.exception.HdmException500;
//import lombok.RequiredArgsConstructor;
//
//
///**
// * En esta clase se espera tener lógica correspondiente al manejo de la respuesta al consumo de otros microservicios.
// * Cada método puede o no incluir un manejador de errores usando la anotacion {@link HystrixCommand}.
// * @author ${user}
// * @since ${date}
// */
//@Service
//@RequiredArgsConstructor
//public class CorreosRemotoService {
//
//	private final CorreosRemotoRepository correosRemotoRepository;
//	
//	private static final String CODIGO_ERROR_MICROSERVICIO = "[CORREOS]";
//
//	
//	/**
//	 * La anotacion {@link HystrixCommand} permite habilitar circuit-breaker
//	 * fallbackMethod si sucede una excepción. La excepción se dispara siempre que
//	 * el otro microservicio haya generado un error, al ignorar dicha excepción
//	 * estamos diciendo que no se ejecute el metodo "findByBrmFallback" cuando
//	 * suceda.
//	 * 
//	 * @param datos de entrada
//	 * @return El modelo de datos.
//	 */
//	@HystrixCommand(fallbackMethod = "enviarCorreoFallback")
//	public void enviarCorreo(EjemploDTO body) {
//		correosRemotoRepository.enviarCorreo(body);
//	}
//
//	/**
//	 * El metodo que será ejecutado si "enviarCorreo" falla 
//	 * Este metodo sirve para generar una respuesta alterna en caso de error.
//	 * @param parametros de entrada
//	 * @param e La excepción que fue generada.
//	 * @return Un objeto del mismo tipo que el metodo principal.
//	 */
//	protected void enviarCorreoFallback(EjemploDTO body, Throwable e) {
//	}
//	
//	
//	/**
//	 * La anotacion {@link HystrixCommand} permite habilitar circuit-breaker
//	 * 
//	 * {@link RemoteCallAspect} si sucede una excepción analiza el error sucedido en
//	 * el ms remoto y propaga un error amigable para front end, la anotación propaga
//	 * una excepcion de tipo {@link HdmException500} con el mensaje de error
//	 * adecuado para el usuario, tambien pinta en logs el error real.
//	 * 
//	 * @param datos de entrada
//	 * @return El modelo de datos.
//	 */
//	@HystrixCommand
//	@RemoteCall(CODIGO_ERROR_MICROSERVICIO)
//	public void enviarCorreoRemoto(EjemploDTO body) {
//		correosRemotoRepository.enviarCorreo(body);
//	}
//	
//	
//	
//}
