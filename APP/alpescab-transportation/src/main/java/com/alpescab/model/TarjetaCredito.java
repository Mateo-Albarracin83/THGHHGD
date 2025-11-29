package com.alpescab.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TarjetaCredito")
public class TarjetaCredito {

    @Id
    @Column(name = "NUMERO")
    private Long numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CEDULA", nullable = false)
    private Usuario usuario;

    @Column(name = "CODIGOSEGURIDAD", nullable = false)
    private String codigoSeguridad;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHAVENCIMIENTO")
    private Date fechaVencimiento;

    // Getters y Setters
    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(String codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
