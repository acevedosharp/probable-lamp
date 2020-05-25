package sabana.mrp1.entities;

import javax.persistence.*;

@Entity
@Table(name = "registro_ventas", schema = "mrp")
public class RegistroVentasPersist {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Column(name = "inventario_id") Integer inventarioId;

    private @Column(name = "tipo") String tipo;
    private @Column(name = "tiempo") String tiempo;
    @SuppressWarnings("JpaAttributeTypeInspection") private @ManyToOne @JoinColumn(name = "producto") Producto producto;

    public RegistroVentasPersist() {
    }

    public RegistroVentasPersist(Integer inventarioId, String tipo, String tiempo, Producto producto) {
        this.inventarioId = inventarioId;
        this.tipo = tipo;
        this.tiempo = tiempo;
        this.producto = producto;
    }

    public Integer getInventarioId() {
        return inventarioId;
    }

    public void setInventarioId(Integer inventarioId) {
        this.inventarioId = inventarioId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
