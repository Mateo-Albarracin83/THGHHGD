package com.alpescab.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TransporteMercancias")
public class TransporteMercancias {

    @Id
    @Column(name = "idServicio")
    private Long idServicio;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "idServicio")
    private Servicio servicio;

    @Column(name = "tarifaMercancias", nullable = false)
    private Double tarifaMercancias;

    // Getters y Setters
    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Double getTarifaMercancias() {
        return tarifaMercancias;
    }

    public void setTarifaMercancias(Double tarifaMercancias) {
        this.tarifaMercancias = tarifaMercancias;
    }
}
