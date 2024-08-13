package org.esfe.repositorios;

import org.esfe.modelos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
public interface IProductoRepository extends JpaRepository<Producto, Integer>{
}
