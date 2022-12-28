//package com.honda.hdm.warrantiesmg.remoto.repository;
//
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.honda.hdm.warrantiesmg.remoto.dto.EjemploDTO;
//
///**
// * Interface que nos permite conectar con otros microservicios, usando solo configuración.
// * La url se conforma del valor de {@link FeignClient}
// * @author ${user}
// * @since ${date}
// */
//@FeignClient("correos")
//public interface CorreosRemotoRepository {
//
//	
//	@PostMapping("/pruebaliqui4/ejemplos/enviar")
//	void enviarCorreo(@RequestBody EjemploDTO body);
//	
//}
