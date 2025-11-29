package com.alpescab.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Servicio")
public class Servicio {

    @Id
    @Column(name = "IDSERVICIO")
    private Long idServicio;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "DISTANCIA")
    private Double distancia;

    @Column(name = "TARIFA")
    private Double tarifa;

    @Column(name = "VALORSERVICIO", insertable = false, updatable = false)
    private Double valorServicio;

    @Column(name = "HORAINICIO")
    private LocalDateTime horaInicio;

    @Column(name = "HORAFIN")
    private LocalDateTime horaFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLACA")
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENTECEDULA", nullable = false)
    private Usuario cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONDUCTORCEDULA")
    private Usuario conductor;

    // Getters y Setters

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public Double getTarifa() {
        return tarifa;
    }

    public void setTarifa(Double tarifa) {
        this.tarifa = tarifa;
    }

    public Double getValorServicio() {
        return valorServicio;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalDateTime horaFin) {
        this.horaFin = horaFin;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Usuario getConductor() {
        return conductor;
    }

    public void setConductor(Usuario conductor) {
        this.conductor = conductor;
    }
}
