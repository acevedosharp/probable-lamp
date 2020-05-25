package sabana.mrp1.entities;

import lombok.*;

import javax.persistence.*;

@ToString
@Entity
@Table(name = "relacion_ingrediente_producto", schema = "mrp")
public class IngredienteProducto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Column(name = "rel_ing_prod_id") Integer relIngProdId;

    @SuppressWarnings("JpaAttributeTypeInspection") private @ManyToOne @JoinColumn(name = "producto") Producto producto;
    private @ManyToOne @JoinColumn(name = "ingrediente") Ingrediente ingrediente;
    private @Column(name = "cantidad") Integer cantidad;

    public IngredienteProducto() {
    }

    public IngredienteProducto(Integer relIngProdId, Producto producto, Ingrediente ingrediente, Integer cantidad) {
        this.relIngProdId = relIngProdId;
        this.producto = producto;
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
    }

    public Integer getRelIngProdId() {
        return relIngProdId;
    }

    public void setRelIngProdId(Integer relIngProdId) {
        this.relIngProdId = relIngProdId;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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

    public String getNombreIngrediente(){
        return ingrediente.getNombre();
    }

    public String getMetricaIngrediente() { return ingrediente.getMetrica(); }

}