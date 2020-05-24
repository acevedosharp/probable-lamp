package sabana.mrp1.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "relacion_ingrediente_producto", schema = "mrp")
public class IngredienteProducto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Column(name = "rel_ing_prod_id") Integer relIngProdId;

    private @ManyToOne @JoinColumn(name = "producto") Producto producto;
    private @ManyToOne @JoinColumn(name = "ingrediente") Ingrediente ingrediente;
    private @Column(name = "cantidad") Integer cantidad;

    public String getNombreIngrediente(){
        return ingrediente.getNombre();
    }

}
