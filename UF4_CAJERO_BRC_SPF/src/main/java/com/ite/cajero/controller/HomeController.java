package com.ite.cajero.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ite.cajero.modelo.beans.Cuenta;
import com.ite.cajero.modelo.dao.CuentaDao;


@Controller
public class HomeController {
	
	@Autowired
	private CuentaDao cDao;
	
	/* Muestra la pagina de login*/
	
	@GetMapping("/")
	public String mostrarLogin() {
		return "login";
	}
	
	/* Si la cuenta no existe no se le deja seguir adelante, y se mostrará el 
	  mensaje ‘Cuenta no existe’. Si la cuenta existe, aparece un menú con 
	 las opciones que se pueden realizar desde el cajero*/
	
	
	@PostMapping("/login")
	public String login(@RequestParam("idCuenta") int idCuenta, RedirectAttributes attr,
			HttpSession sesion) {
		Cuenta cuenta = cDao.validarCuenta(idCuenta);
		if (cuenta != null) {
			sesion.setAttribute("cuenta", cuenta); //guardamos solo el id porque la cuenta va a sufrir modificaciones 
			return "redirect:/cliente";
		} else {
			attr.addFlashAttribute("mensajeError", "Cuenta no existe");
			return "redirect:/";
		}
	}
	
	/*Cierra la sesion de usuario y volvemos a la pagina de login para comenzar
	 con otro usuario */
	
	@GetMapping("/cerrarSesion")
	public String cerrarSesion(HttpSession sesion) {
		sesion.invalidate();
		return "redirect:/";
	}
	
	
}
