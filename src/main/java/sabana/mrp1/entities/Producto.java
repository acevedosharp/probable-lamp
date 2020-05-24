package sabana.mrp1.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "producto", schema = "mrp")
public class Producto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Column(name = "producto_id") Integer productoId;

    private @Column(name = "nombre") String nombre;
    private @Column(name = "precio_venta") Integer precioVenta;
    private @Column(name = "existencias") Integer existencias;

    @OneToMany(mappedBy = "producto",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<IngredienteProducto> ingredientesProducto;

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

    public String getNombre(){
        return nombre;
    }

    @Override public String toString() {
        return nombre;
    }
}