package org.esfe.servicios.implementaciones;

import org.esfe.modelos.Ventas;
import org.esfe.repositorios.IVentasRepository;
import org.esfe.servicios.interfaces.IVentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentasService implements IVentasService {

    @Autowired
    private IVentasRepository ventasRepository;

    @Override
    public Page<Ventas> buscarTodosPaginados(Pageable pageable) {
        return ventasRepository.findAll(pageable);
    }

    @Override
    public List<Ventas> obtenerTodasLasVentas() {
        return ventasRepository.findAll();
    }

    @Override
    public Optional<Ventas> obtenerVentaPorId(Integer id) {
        return ventasRepository.findById(id);
    }

    @Override
    public Ventas crearOEditar(Ventas venta) {
        return ventasRepository.save(venta);
    }

    @Override
    public void eliminarPorId(Integer id) {
        ventasRepository.deleteById(id);
    }
}
