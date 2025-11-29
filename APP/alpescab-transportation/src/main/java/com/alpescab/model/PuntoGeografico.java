package com.alpescab.model;

import jakarta.persistence.*;

@Entity
@Table(name="PUNTOGEOGRAFICO")
public class PuntoGeografico {
    @Id
    @Column(name = "IDPUNTO")
    private Long idPunto;

    @Column(name="NOMBRE", nullable = false)
    private String nombre;

    @Column(name="DIRECCION", nullable = false)
    private String direccion;

    @Column(name="LATITUD", nullable = false)
    private double latitud;

    @Column(name="LONGITUD", nullable = false)
    private double longitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CIUDAD")
    private City ciudad;

    //Getters y Setters
    public Long getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(Long idPunto) {
        this.idPunto = idPunto;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion(){
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public City getCiudad() {
        return ciudad;
    }

    public void setCiudad(City ciudad) {
        this.ciudad = ciudad;
    }
}
