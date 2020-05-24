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
public class RegistroVentas {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Column(name = "inventario_id") Integer inventarioId;
    private @Column(name = "tipo") String tipo;
    private @Column(name = "tiempo") String tiempo;
    private @ManyToOne @JoinColumn(name = "producto") Producto producto;
    @OneToMany(mappedBy = "registroVentas",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ComportamientoMes> comportamientosMes;

    public String getNombreProducto(){
        return getProducto().getNombre();
    }

    public Integer getEnero(){
        return comportamientosMes.get(0).getVentas();
    }

    public Integer getFebrero(){
        return comportamientosMes.get(1).getVentas();
    }

    @Override public String toString() {
        StringBuilder res = new StringBuilder();

        for(ComportamientoMes item : comportamientosMes) {
            res.append(item.toString()).append(", ");
        }

        return res.substring(0, res.length() - 2);
    }
}
