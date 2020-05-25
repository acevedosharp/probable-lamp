package sabana.mrp1.entities;

import javax.persistence.*;

@Entity
@Table(name = "comportamiento_mes", schema = "mrp")
public class ComportamientoMes {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id @Column(name = "comportamiento_mes_id") Integer productoId;

    private @ManyToOne @JoinColumn(name = "registro_ventas") RegistroVentas registroVentas;
    private @Column(name = "mes") Integer mes;
    private @Column(name = "ventas") Double ventas;

    public ComportamientoMes() {
    }
    public ComportamientoMes(Integer productoId, RegistroVentas registroVentas, Integer mes, Double ventas) {
        this.productoId = productoId;
        this.registroVentas = registroVentas;
        this.mes = mes;
        this.ventas = ventas;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public RegistroVentas getRegistroVentas() {
        return registroVentas;
    }

    public void setRegistroVentas(RegistroVentas registroVentas) {
        this.registroVentas = registroVentas;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Double getVentas() {
        return ventas;
    }

    public void setVentas(Double ventas) {
        this.ventas = ventas;
    }

    @Override public String toString() {
        return "Mes: " + mes + " - " + "Ventas: " + ventas;
    }
}

