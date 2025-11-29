package com.alpescab.model;

import jakarta.persistence.*;

@Entity
@Table(name = "EntregaDomicilio")
public class EntregaDomicilio {

    @Id
    @Column(name = "idServicio")
    private Long idServicio;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "idServicio")
    private Servicio servicio;

    @Column(name = "tarifaDomicilio")
    private Double tarifaDomicilio;

    @Column(name = "ubicacionRestaurante", length = 200)
    private String ubicacionRestaurante;

    @Column(name = "puntoUsuario", length = 200)
    private String puntoUsuario;

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
    public Double getTarifaDomicilio() {
        return tarifaDomicilio;
    }
    public void setTarifaDomicilio(Double tarifaDomicilio) {
        this.tarifaDomicilio = tarifaDomicilio;
    }
    public String getUbicacionRestaurante() {
        return ubicacionRestaurante;
    }
    public void setUbicacionRestaurante(String ubicacionRestaurante) {
        this.ubicacionRestaurante = ubicacionRestaurante;
    }
    public String getPuntoUsuario() {
        return puntoUsuario;
    }
    public void setPuntoUsuario(String puntoUsuario) {
        this.puntoUsuario = puntoUsuario;
    }
}
