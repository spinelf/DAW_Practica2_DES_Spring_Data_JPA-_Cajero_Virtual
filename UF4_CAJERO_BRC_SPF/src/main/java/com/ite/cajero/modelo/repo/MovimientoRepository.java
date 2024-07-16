
package com.ite.cajero.modelo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ite.cajero.modelo.beans.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer>{
	@Query("select m from Movimiento m where m.cuenta.idCuenta = ?1 order by m.fecha desc")
	public List<Movimiento> findByIdCuenta(int idCuenta);

}
