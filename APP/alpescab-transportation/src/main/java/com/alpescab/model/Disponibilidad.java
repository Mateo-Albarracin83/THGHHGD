package com.alpescab.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Disponibilidad")
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDDISPONIBILIDAD")
    private Long idDisponibilidad;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLACA", nullable = false)
    private Vehiculo vehiculo;

    @Column(name = "HORAINICIO")
    private LocalDateTime horaInicio;

    @Column(name = "HORAFIN")
    private LocalDateTime horaFin;

    @Column(name = "TIPOSERVICIO")
    private String tipoServicio;

    @Column(name = "ASIGNADA")
    private Integer asignada = 0;

    // Getters y Setters
    public Long getIdDisponibilidad() {
        return idDisponibilidad;
    }

    public void setIdDisponibilidad(Long idDisponibilidad) {
        this.idDisponibilidad = idDisponibilidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
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

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Integer getAsignada() {
        return asignada;
    }

    public void setAsignada(Integer asignada) {
        this.asignada = asignada;
    }

    // Helpers para trabajar con booleanos en Java
    @Transient
    public boolean isAsignada() {
        return asignada != null && asignada == 1;
    }

    public void setAsignadaBoolean(boolean value) {
        this.asignada = value ? 1 : 0;
    }
}
