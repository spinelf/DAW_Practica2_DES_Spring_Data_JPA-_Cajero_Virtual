
package com.ite.cajero.modelo.dao;

import java.util.List;

import com.ite.cajero.modelo.beans.Cuenta;
import com.ite.cajero.modelo.beans.Movimiento;

public interface CuentaDao {
	Cuenta findById(int idCuenta);
	Cuenta validarCuenta(int idCuenta);
	int ingresar (Movimiento movimiento);
	int retirar (Movimiento movimiento);
	int transferencia(Cuenta origen, Cuenta destino, int cantidad);
	List<Movimiento> fetchMovimientos(int idCuenta);
}
