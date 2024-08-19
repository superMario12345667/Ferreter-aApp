package org.esfe.modelos;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table (name="productos")

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "el nombre es requerido")
    private String nombre;

    @NotBlank(message = "el nombre es requerido")

    @NotBlank(message = "la descripcion es requerida")
    private String descripcion;

    private int precio;
    private int stock;
    private int idCategoria;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "el nombre es requerido") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "el nombre es requerido") String nombre) {
        this.nombre = nombre;
    }

    public @NotBlank(message = "el nombre es requerido") @NotBlank(message = "la descripcion es requerida") String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NotBlank(message = "el nombre es requerido") @NotBlank(message = "la descripcion es requerida") String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
