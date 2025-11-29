package com.alpescab.model;

import jakarta.persistence.*;

@Entity
@Table(name = "USUARIO")
public class Usuario {


    @Id
    @Column(name = "CEDULA", nullable = false, length = 20)
    private String cedula;

    @Column(name = "CORREO", nullable = false, unique = true)
    private String correo;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "CELULAR")
    private String celular;

    @Column(name = "TIPO", nullable = false)
    private String tipo; 

    @Column(name = "CALIFICACION")
    private Double calificacion;

    @Column(name = "COMENTARIO", length = 4000)
    private String comentario;

    // Getters y Setters
    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCelular() {
        return celular;
    }
    public void setCelular(String celular) {
        this.celular = celular;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Double getCalificacion() {
        return calificacion;
    }
    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}

