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

    private @Id @Column(name = "rel_ing_prod_id") Integer relIngProdId;

    private @Column(name = "producto") Integer productoId;
    private @ManyToOne @JoinColumn(name = "ingrediente") Ingrediente ingrediente;
    private @Column(name = "cantidad") String cantidad;
    private @Column(name = "metrica") String metrica;

}
