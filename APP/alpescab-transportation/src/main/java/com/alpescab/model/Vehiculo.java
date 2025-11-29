package com.alpescab.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Vehiculo")
public class Vehiculo {

    @Id
    @Column(name = "PLACA")
    private String placa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CEDULA", nullable = false)
    private Usuario conductor;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "MARCA")
    private String marca;

    @Column(name = "MODELO")
    private String modelo;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "CAPACIDADPASAJEROS")
    private Integer capacidadPasajeros;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CIUDADEXPEDICION")
    private City ciudadExpedicion;

    // Getters y Setters
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Usuario getConductor() {
        return conductor;
    }

    public void setConductor(Usuario conductor) {
        this.conductor = conductor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getCapacidadPasajeros() {
        return capacidadPasajeros;
    }

    public void setCapacidadPasajeros(Integer capacidadPasajeros) {
        this.capacidadPasajeros = capacidadPasajeros;
    }

    public City getCiudadExpedicion() {
        return ciudadExpedicion;
    }

    public void setCiudadExpedicion(City ciudadExpedicion) {
        this.ciudadExpedicion = ciudadExpedicion;
    }
}
