package sabana.mrp1.entities;

import lombok.*;

import javax.persistence.*;

@ToString
@Entity
@Table(name = "producto", schema = "mrp")
public class ProductoPersist {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Column(name = "producto_id") Integer productoId;

    private @Column(name = "nombre") String nombre;
    private @Column(name = "precio_venta") Integer precioVenta;
    private @Column(name = "existencias") Integer existencias;

    public ProductoPersist() {
    }

    public ProductoPersist(Integer productoId, String nombre, Integer precioVenta, Integer existencias) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.existencias = existencias;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Integer precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Integer getExistencias() {
        return existencias;
    }

    public void setExistencias(Integer existencias) {
        this.existencias = existencias;
    }
}
