package JSIA.WebMoteros.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import JSIA.WebMoteros.dtos.ClubDto;
import JSIA.WebMoteros.services.ApiService;

/**
 * Clase controladora de comunicar con la api los métodos del club
 */
@Controller
public class ClubController {
	
	JSIA.WebMoteros.services.EncriptarService servicios = 	new JSIA.WebMoteros.services.EncriptarService();
	
	@Autowired
	private ApiService apiService;
	
	@PostMapping("/darAltaClub")
		public String darAltaClub(@RequestParam String nombreClub, @RequestParam String coloresClub, @RequestParam String mailClub, @RequestParam String contraseniaClub) {
		
		ClubDto nuevoClub = new ClubDto(  nombreClub,  coloresClub,   mailClub,   servicios.encriptarContraseña(contraseniaClub));
		
		String respuesta = apiService.enviarRegistroClub(nuevoClub);
		
		if("success".equalsIgnoreCase(respuesta)) {
			
			return "redirect:/ventanaPrincipal";
		}else {
			
			return "redirect:/login?error";
		}
	}
	
}
