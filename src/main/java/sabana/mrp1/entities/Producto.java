package sabana.mrp1.entities;

import javax.persistence.*;
import java.util.Set;

@Table(name = "producto", schema = "mrp")
public class Producto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Column(name = "producto_id") Integer productoId;

    private @Column(name = "nombre") String nombre;
    private @Column(name = "precio_venta") Integer precioVenta;
    private @Column(name = "existencias") Integer existencias;

    @OneToMany(mappedBy = "producto",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<IngredienteProducto> ingredientesProducto;

    public Producto() {
    }

    public Producto(String nombre, Integer precioVenta, Integer existencias, Set<IngredienteProducto> ingredientesProducto) {
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.existencias = existencias;
        this.ingredientesProducto = ingredientesProducto;
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

    public Set<IngredienteProducto> getIngredientesProducto() {
        return ingredientesProducto;
    }

    public void setIngredientesProducto(Set<IngredienteProducto> ingredientesProducto) {
        this.ingredientesProducto = ingredientesProducto;
    }

    public String getIngredientesRepresentation() {
        if (ingredientesProducto.size() > 0) {
            StringBuilder res = new StringBuilder();
            // Ojalá Java tuviera String Interpolation...
            for (IngredienteProducto ingredienteProducto : ingredientesProducto) {
                res.append(ingredienteProducto.getIngrediente().getNombre()).append(" x").append(ingredienteProducto.getCantidad()).append(" ").append(ingredienteProducto.getIngrediente().getMetrica());
                res.append(", ");
            }
            return res.substring(0, res.length() - 2);
        } else return "";
    }

    public String getNombreProducto(){
        return getNombre();
    }

    @Override public String toString() {
        return nombre;
    }
}