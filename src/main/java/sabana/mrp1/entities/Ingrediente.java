package sabana.mrp1.entities;

import javax.persistence.*;

@Entity
@Table(name = "ingrediente", schema = "mrp")
public class Ingrediente {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Column(name = "ingrediente_id") Integer ingredienteId;

    private @Column(name = "nombre") String nombre;
    private @Column(name = "metrica") String metrica;
    private @Column(name = "precio_unidad") Integer precioUnidad;
    private @Column(name = "existencias") Integer existencias;

    public Ingrediente() {
    }

    public Ingrediente(Integer ingredienteId, String nombre, String metrica, Integer precioUnidad, Integer existencias) {
        this.ingredienteId = ingredienteId;
        this.nombre = nombre;
        this.metrica = metrica;
        this.precioUnidad = precioUnidad;
        this.existencias = existencias;
    }

    public Integer getIngredienteId() {
        return ingredienteId;
    }

    public void setIngredienteId(Integer ingredienteId) {
        this.ingredienteId = ingredienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMetrica() {
        return metrica;
    }

    public void setMetrica(String metrica) {
        this.metrica = metrica;
    }

    public Integer getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(Integer precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public Integer getExistencias() {
        return existencias;
    }

    public void setExistencias(Integer existencias) {
        this.existencias = existencias;
    }

    @Override public String toString() {
        return nombre + " (" + metrica + ")";
    }

}