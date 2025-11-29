package com.alpescab.model;

import jakarta.persistence.*;

@Entity
@Table(name = "UsuarioCliente")
public class UsuarioCliente {
    
    @Id
    @Column(name = "CEDULA")
    private String cedula;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "CEDULA")
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
