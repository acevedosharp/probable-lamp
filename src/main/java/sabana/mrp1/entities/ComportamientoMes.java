package sabana.mrp1.entities;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comportamiento_mes", schema = "mrp")
public class ComportamientoMes {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Column(name = "comportamiento_mes_id") Integer productoId;

    private @ManyToOne @JoinColumn(name = "registro_ventas") RegistroVentas registroVentas;
    private @ManyToOne @JoinColumn(name = "producto") Producto producto;
    private @Column(name = "mes") Integer mes;
    private @Column(name = "ventas") Integer ventas;

    @Override public String toString() {
        return "Mes: " + mes + " - " + "Ventas: " + ventas;
    }
}
