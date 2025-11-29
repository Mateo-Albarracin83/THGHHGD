package com.alpescab.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TransportePasajeros")
public class TransportePasajeros {

    @Id
    @Column(name = "idServicio")
    private Long idServicio;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "idServicio")
    private Servicio servicio;

    @Column(name = "tarifaPasajeros", nullable = false)
    private Double tarifaPasajeros;

    @Column(name = "nivelTransporte", nullable = false)
    private String nivelTransporte;

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

    public Double getTarifaPasajeros() {
        return tarifaPasajeros;
    }

    public void setTarifaPasajeros(Double tarifaPasajeros) {
        this.tarifaPasajeros = tarifaPasajeros;
    }

    public String getNivelTransporte() {
        return nivelTransporte;
    }

    public void setNivelTransporte(String nivelTransporte) {
        this.nivelTransporte = nivelTransporte;
    }
}
