package com.alpescab.model;

import jakarta.persistence.*;

@Entity
@Table(name = "UsuarioCoductor")
public class UsuarioConductor {

    @Id
    @Column(name = "cedula")
    private String cedula;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "cedula")
    private Usuario usuario;

    // Getters y Setters
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
