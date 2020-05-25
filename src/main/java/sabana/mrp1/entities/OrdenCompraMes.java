package sabana.mrp1.entities;

import javax.persistence.*;

@Entity
@Table(name = "orden_compra_mes", schema = "mrp")
public class OrdenCompraMes {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Column(name = "orden_compra_id") Integer ordenCompraId;

    private @ManyToOne @JoinColumn(name = "ingrediente") Ingrediente ingrediente;
    private @Column(name = "cantidad") Integer cantidad;
    private @Column(name = "mes") Integer mes;

    public OrdenCompraMes() {
    }

    public OrdenCompraMes(Integer ordenCompraId, Ingrediente ingrediente, Integer cantidad, Integer mes) {
        this.ordenCompraId = ordenCompraId;
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
        this.mes = mes;
    }

    public Integer getOrdenCompraId() {
        return ordenCompraId;
    }

    public void setOrdenCompraId(Integer ordenCompraId) {
        this.ordenCompraId = ordenCompraId;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }
}
