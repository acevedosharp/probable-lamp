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

    public String getTipoRegistro(){
        return getTipo();
    }

    public String getTiempoRegistro(){
        return getTiempo();
    }

    public Double getEnero(){
        return comportamientosMes.get(0).getVentas();
    }

    public Double getFebrero(){
        return comportamientosMes.get(1).getVentas();
    }

    public Double getMarzo(){
        return comportamientosMes.get(2).getVentas();
    }

    public Double getAbril(){
        return comportamientosMes.get(3).getVentas();
    }

    public Double getMayo(){
        return comportamientosMes.get(4).getVentas();
    }

    public Double getJunio(){
        return comportamientosMes.get(5).getVentas();
    }

    public Double getJulio(){
        return comportamientosMes.get(6).getVentas();
    }

    public Double getAgosto(){
        return comportamientosMes.get(7).getVentas();
    }

    public Double getSeptiembre(){
        return comportamientosMes.get(8).getVentas();
    }

    public Double getOctubre(){
        return comportamientosMes.get(9).getVentas();
    }

    public Double getNoviembre(){
        return comportamientosMes.get(10).getVentas();
    }

    public Double getDiciembre(){
        return comportamientosMes.get(11).getVentas();
    }

    @Override public String toString() {
        StringBuilder res = new StringBuilder();

        for(ComportamientoMes item : comportamientosMes) {
            res.append(item.toString()).append(", ");
        }

        return res.substring(0, res.length() - 2);
    }
}
