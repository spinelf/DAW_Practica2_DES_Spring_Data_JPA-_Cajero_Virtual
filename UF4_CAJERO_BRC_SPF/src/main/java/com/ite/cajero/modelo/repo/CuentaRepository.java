
package com.ite.cajero.modelo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ite.cajero.modelo.beans.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer>{

}
