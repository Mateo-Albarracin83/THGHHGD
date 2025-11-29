package com.alpescab.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Rating")
public class Rating {

    @Id
    @Column(name = "IDCOMENTARIO")
    private Long idComentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDSERVICIO", nullable = false)
    private Servicio servicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revisor", nullable = false)
    private Usuario revisor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revisado", nullable = false)
    private Usuario revisado;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Lob
    @Column(name = "comentario")
    private String comentario;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha")
    private Date fecha;

    // Getters y Setters
    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Usuario getRevisor() {
        return revisor;
    }

    public void setRevisor(Usuario revisor) {
        this.revisor = revisor;
    }

    public Usuario getRevisado() {
        return revisado;
    }

    public void setRevisado(Usuario revisado) {
        this.revisado = revisado;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
