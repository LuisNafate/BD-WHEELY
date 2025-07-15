package com.wheely.model;

/**
 * Modelo de datos para la tabla TipoReporte
 * Representa los tipos de reportes disponibles (Incidencia, Sugerencia, Queja, etc.)
 */
public class TipoReporte {
    private int idTipoReporte;
    private String nombreTipo;
    private String descripcion;

    // Constructor vacío
    public TipoReporte() {}

    // Constructor completo
    public TipoReporte(int idTipoReporte, String nombreTipo, String descripcion) {
        this.idTipoReporte = idTipoReporte;
        this.nombreTipo = nombreTipo;
        this.descripcion = descripcion;
    }

    // Constructor sin ID
    public TipoReporte(String nombreTipo, String descripcion) {
        this.nombreTipo = nombreTipo;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getIdTipoReporte() {
        return idTipoReporte;
    }

    public void setIdTipoReporte(int idTipoReporte) {
        this.idTipoReporte = idTipoReporte;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "TipoReporte{" +
                "idTipoReporte=" + idTipoReporte +
                ", nombreTipo='" + nombreTipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}