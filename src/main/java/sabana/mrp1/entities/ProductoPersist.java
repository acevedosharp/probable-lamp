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
public class ProductoPersist {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Column(name = "producto_id") Integer productoId;

    private @Column(name = "nombre") String nombre;
    private @Column(name = "precio_venta") Integer precioVenta;
    private @Column(name = "existencias") Integer existencias;

}
