package com.alpescab.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CIUDAD")
public class City {

    public City() { }

    public City(Long idCiudad, String nombre){
        this.idCiudad = idCiudad;
        this.nombre = nombre;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // identity en Oracle
    @Column(name = "IDCIUDAD")
    private Long idCiudad;

    @Column(name = "NOMBRE")
    private String nombre;

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
