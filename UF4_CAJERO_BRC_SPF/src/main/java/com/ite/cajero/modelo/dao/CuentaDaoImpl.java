
package com.ite.cajero.modelo.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ite.cajero.modelo.beans.Cuenta;
import com.ite.cajero.modelo.beans.Movimiento;
import com.ite.cajero.modelo.repo.CuentaRepository;
import com.ite.cajero.modelo.repo.MovimientoRepository;

@Service
public class CuentaDaoImpl implements CuentaDao{
	
	@Autowired
	private CuentaRepository cRepo;
	
	@Autowired
	private MovimientoRepository mRepo;

	//Busca una cuenta por su id
	@Override
	public Cuenta findById(int idCuenta) {
		return cRepo.findById(idCuenta).orElse(null);
	}
	
	//Valida si una cuenta existe o no
	@Override
	public Cuenta validarCuenta(int idCuenta) {
		return findById(idCuenta);
	}

	//Realiza un ingreso y nos guarda el movimiento y el apunte en la cuenta.
	@Override
	public int ingresar (Movimiento movimiento) {
		Cuenta cuenta = movimiento.getCuenta();
		int filas = 0;
		try {
			mRepo.save(movimiento);
			cuenta.setSaldo(cuenta.getSaldo() + movimiento.getCantidad());
			cRepo.save(cuenta);
			filas = 2;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		return filas;
	}

	//realiza una retirada de dinero comprobando previamente si hay saldo en la cuenta
	
	@Override
	public int retirar (Movimiento movimiento) {
		if (movimiento.getCantidad() > movimiento.getCuenta().getSaldo()) return -1;
		int filas =0;
		Cuenta cuenta = movimiento.getCuenta();
		
		try {
			mRepo.save(movimiento);
			cuenta.setSaldo(cuenta.getSaldo() - movimiento.getCantidad());
			cRepo.save(cuenta);
			filas = 2;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		return filas;
	}

	/*realiza una transferencia realizando la comprobacion de que exista la cuenta 
	 de destino, que ésta no sea la misma que la de origen y que haya saldo en la cuenta*/
	
	@Override
	public int transferencia(Cuenta origen, Cuenta destino, int cantidad) {
		if (destino == null) return -2;
		if (origen.getSaldo() < cantidad) return -1;
		if (origen.equals(destino)) return 0;
		int filas = 0;
		
		Movimiento extraccion = new Movimiento (origen, cantidad, "transferencia e");
		extraccion.setFecha(new Date());
		Movimiento ingreso = new Movimiento (destino, cantidad, "transferencia recibida");
		ingreso.setFecha(new Date());
		
		int resultadoCargo = retirar (extraccion);
		if (resultadoCargo != 2) return resultadoCargo;
		filas += resultadoCargo;
		
		int resultadoAbono = ingresar (ingreso);
		if (resultadoAbono != 2) {
			Movimiento deshacerCargo = new Movimiento();
			deshacerCargo.setCantidad(cantidad);
			deshacerCargo.setCuenta(origen);
			deshacerCargo.setOperacion("devolucion");
			deshacerCargo.setFecha(new Date());
			ingresar (deshacerCargo);
			return resultadoAbono;
		};
		filas += resultadoAbono;		
		return filas;
	}

	//devuelve los movimientos de una cuenta según si id
	@Override
	public List<Movimiento> fetchMovimientos(int idCuenta) {
		return mRepo.findByIdCuenta(idCuenta);


	}
	

}
