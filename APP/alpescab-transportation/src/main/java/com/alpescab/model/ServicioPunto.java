package com.alpescab.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="SERVICIOPUNTO")
public class ServicioPunto {
    @Id
    @Column(name ="IDSERVICIOPUNTO")
    private Long idServicioPunto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="IDSERVICIO")
    private Servicio servicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="IDPUNTO")
    private PuntoGeografico punto;

    @Column(name="ORDEN")
    private Long orden;

    //Getters y setters

    public Long getIdServicioPunto() {
        return idServicioPunto;
    }

    public void setIdServicioPunto(Long idServicioPunto) {
        this.idServicioPunto = idServicioPunto;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public PuntoGeografico getPunto() {
        return punto;
    }

    public void setPunto(PuntoGeografico punto) {
        this.punto = punto;
    }

    public Long getOrden() {
        return orden;
    }

    public void setOrden(Long orden){
        this.orden = orden;
    }
}
