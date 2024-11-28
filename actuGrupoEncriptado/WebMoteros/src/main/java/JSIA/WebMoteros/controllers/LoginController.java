package JSIA.WebMoteros.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import JSIA.WebMoteros.dtos.LoginRequestDto;
import JSIA.WebMoteros.services.ApiService;
import JSIA.WebMoteros.services.EncriptarService;
@Controller
public class LoginController {
	JSIA.WebMoteros.services.EncriptarService servicios = 	new JSIA.WebMoteros.services.EncriptarService();

	@Autowired
	private ApiService apiService;

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	@PostMapping("/login")
	public String handleLogin(@RequestParam String mail, @RequestParam String contrasenya) {

		LoginRequestDto loginRequest = new LoginRequestDto(mail, servicios.encriptarContraseña(contrasenya) );

		String response = apiService.sendLoginData(loginRequest, "usuarios");

		if ("success".equalsIgnoreCase(response)) {
			return "redirect:/ventanaPrincipal";
		}

		response = apiService.sendLoginData(loginRequest, "clubes");

		if ("success".equalsIgnoreCase(response)) {
			return "redirect:/ventanaPrincipal";
		} else {
			return "redirect:/login?error";
		}

	}
	
	
}
