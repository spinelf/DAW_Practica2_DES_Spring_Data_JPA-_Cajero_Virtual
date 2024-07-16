
package com.ite.cajero.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ite.cajero.modelo.beans.Cuenta;
import com.ite.cajero.modelo.beans.Movimiento;
import com.ite.cajero.modelo.dao.CuentaDao;


@Controller
@RequestMapping("/cliente")
public class ClienteController {
	
	
	
		
	@Autowired
	private CuentaDao cDao;
	
	//Nos vamos el manú cliente
	
	@GetMapping("")
	public String cliente() {
		return "forward:/cliente/menu";
	}
	
	//Nos muestra el menú con las operaciones que podemos realizar
	
	@GetMapping("/menu")
	public String mostrarMenu() {
		return "menu";
	}
	
	//Página donde podemos introducir el efectivo a retirar
	
	@GetMapping("/extraer")
	public String extraerDinero(Model model) {
		model.addAttribute("transaccion", "extraccion");
		return "transaccion";
	}
	
	//Realizamos la retirada de efectivo y lo apuntamos en la cuenta.
	
	@PostMapping("/extraccion")
	public String extraerDinero(Movimiento movimiento, RedirectAttributes attr,
			HttpSession sesion) {
		movimiento.setFecha(new Date());
		movimiento.setCuenta((Cuenta)sesion.getAttribute("cuenta"));
		
		int resultadoOperacion = cDao.retirar(movimiento);
		String mensaje;
		
		if (resultadoOperacion == -1) {
			mensaje = "Saldo insufciente";
			attr.addFlashAttribute("mensajeError", mensaje);
		} else if (resultadoOperacion > 0) {
			mensaje = "La operación se ha realizado correctamente";
			mensaje += "\nSaldo: " + movimiento.getCuenta().getSaldo();
			attr.addFlashAttribute("mensajeOk", mensaje);
			return "redirect:/cliente/extraer"; 
		} else {
			mensaje = "No se ha podido realizar la operación";
			mensaje += "\nSaldo: " + movimiento.getCuenta().getSaldo();
			attr.addFlashAttribute("mensajeError", mensaje);
		}
		
		return "redirect:/cliente/extraer";
	}
		
	//Página donde podemos introducir el efectivo a ingresar
	
	@GetMapping("/ingresar")
	public String mostrarIngresarDinero(Model model) {
		model.addAttribute("transaccion", "ingreso");
		return "transaccion";
	}
	
	//Realizamos el ingreso de efectivo y lo apuntamos en la cuenta.
	
	@PostMapping("/ingreso")
	public String ingresarDinero(Movimiento movimiento, RedirectAttributes attr,
			HttpSession sesion) {
		movimiento.setFecha(new Date());
		movimiento.setCuenta((Cuenta) sesion.getAttribute("cuenta"));
		String mensaje;
		if (cDao.ingresar(movimiento) > 0) {
			mensaje = "La operación se ha realizado correctamente";
			mensaje += "\nSaldo: " + movimiento.getCuenta().getSaldo();
			attr.addFlashAttribute("mensajeOk", mensaje);
		} else {
			mensaje = "No se ha podido realizar la operación";
			mensaje += "\nSaldo: " + movimiento.getCuenta().getSaldo();
			attr.addFlashAttribute("mensajeError", mensaje);
		}
		return "redirect:/cliente/ingresar";
	}
	
	//Página donde podemos realizar la transferencia con una cuenta destino y el importe.
	
	@GetMapping("/transferencia")
	public String mostrarTransferencia() {
		return "transferencia";
	}
		
	/*Realiza una transferencia comprobando si la cuenta destino existe 
	  y si hay saldo en la cuenta origen */
	
	@PostMapping("/transferencia")
	public String realizarTransferencia(@RequestParam("destino") int idCuentaDestino, 
			@RequestParam("cantidad") int cantidad, HttpSession sesion,
			RedirectAttributes attr) {
		Cuenta origen = (Cuenta) sesion.getAttribute("cuenta");
		Cuenta destino = cDao.findById(idCuentaDestino);
		
		int resultadoOperacion = cDao.transferencia(origen, destino, cantidad);
		System.out.println("resultado de la operacion: " + resultadoOperacion);
		
		switch(resultadoOperacion) {
		case -2:
			attr.addFlashAttribute("mensajeError", "La cuenta de destino no existe");
			return "redirect:/cliente/transferencia";
		case -1: 
			attr.addFlashAttribute("mensajeError", "No hay saldo sufuciente en la cuenta de origen");
			return "redirect:/cliente/transferencia";
		case 0:
			attr.addFlashAttribute("mensajeError", "No se pudo realizar la operación");
			return "redirect:/cliente/transferencia";
		default:
			attr.addFlashAttribute("mensajeok", "Operación realizada correctamente.");
			return "redirect:/cliente/transferencia";
		}
	}
	
	//Muestra los movimientos en la cuenta
	
	@GetMapping("/movimientos")
	public String mostrarMovimientos(Model model, HttpSession sesion) {
		Cuenta cuenta = (Cuenta) sesion.getAttribute("cuenta");
		model.addAttribute("listaMovimientos", cDao.fetchMovimientos(cuenta.getIdCuenta()));
		return "movimientos";
	}
}
