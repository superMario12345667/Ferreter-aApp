package org.esfe.servicios.interfaces;

import org.esfe.modelos.Ventas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IVentasService {
    Page<Ventas> buscarTodosPaginados (Pageable pageable);
    List<Ventas> obtenerTodasLasVentas();
    Optional<Ventas> obtenerVentaPorId(Integer id);
    Ventas crearOEditar(Ventas venta);
    void eliminarPorId(Integer id);
}
