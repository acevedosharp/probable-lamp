package sabana.mrp1.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ingrediente", schema = "mrp")
public class Ingrediente {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Column(name = "ingrediente_id") Integer ingredienteId;

    private @Column(name = "nombre") String nombre;
    private @Column(name = "metrica") String metrica;
    private @Column(name = "precio_unidad") Integer precioUnidad;
    private @Column(name = "existencias") Integer existencias;


    @Override public String toString() {
        return nombre + " (" + metrica + ")";
    }

}
