package org.esfe.repositorios;

import org.esfe.modelos.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
public interface IVentasRepository extends JpaRepository<Ventas, Integer> {
}
