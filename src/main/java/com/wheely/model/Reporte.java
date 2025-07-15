package com.wheely.model;

import java.time.LocalDateTime;

/**
 * Modelo de datos para la tabla Reporte
 * Representa un reporte sobre el estado de las rutas de transporte
 */
public class Reporte {
    private int idReporte;
    private int idRuta;
    private int idTipoReporte;
    private int idUsuario;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaReporte;

    // Constructor vacío requerido para Jackson
    public Reporte() {}

    // Constructor completo
    public Reporte(int idReporte, int idRuta, int idTipoReporte, int idUsuario,
                   String titulo, String descripcion, LocalDateTime fechaReporte) {
        this.idReporte = idReporte;
        this.idRuta = idRuta;
        this.idTipoReporte = idTipoReporte;
        this.idUsuario = idUsuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaReporte = fechaReporte;
    }

    // Constructor sin ID y fecha (para creación de nuevos reportes)
    public Reporte(int idRuta, int idTipoReporte, int idUsuario, String titulo, String descripcion) {
        this.idRuta = idRuta;
        this.idTipoReporte = idTipoReporte;
        this.idUsuario = idUsuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public int getIdTipoReporte() {
        return idTipoReporte;
    }

    public void setIdTipoReporte(int idTipoReporte) {
        this.idTipoReporte = idTipoReporte;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDateTime fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "idReporte=" + idReporte +
                ", idRuta=" + idRuta +
                ", idTipoReporte=" + idTipoReporte +
                ", idUsuario=" + idUsuario +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaReporte=" + fechaReporte +
                '}';
    }
}