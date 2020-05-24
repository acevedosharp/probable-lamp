package sabana.mrp1.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registro_ventas", schema = "mrp")
public class RegistroVentasPersist {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Column(name = "inventario_id") Integer inventarioId;

    private @Column(name = "tipo") String tipo;
    private @Column(name = "tiempo") String tiempo;
    private @ManyToOne @JoinColumn(name = "producto") Producto producto;

}
