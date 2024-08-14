package org.esfe.modelos;

import jakarta.persistence.*;

@Entity
public class Ventas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer cantidad;
    private Double total;

    public Ventas() {}

    public Ventas(Integer id, String nombre, String descripcion, Double precio, Integer cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.total = calcularTotal();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        this.total = calcularTotal();
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal() {
        this.total = calcularTotal();
    }

    private Double calcularTotal() {
        return this.cantidad * this.precio;
    }

    @Override
    public String toString() {
        return "Ventas{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                ", total=" + total +
                '}';
    }
}
