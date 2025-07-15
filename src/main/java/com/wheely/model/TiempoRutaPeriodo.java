package com.wheely.model;

/**
 * Modelo de datos para la tabla TiempoRutaPeriodo
 * Representa el tiempo promedio que toma una ruta específica
 * durante un periodo determinado del día
 */
public class TiempoRutaPeriodo {
    private int idTiempoRutaPeriodo;
    private int idRuta;
    private int idPeriodo;
    private int tiempoPromedio; // Tiempo en minutos

    // Constructor vacío requerido para Jackson
    public TiempoRutaPeriodo() {}

    // Constructor completo
    public TiempoRutaPeriodo(int idTiempoRutaPeriodo, int idRuta, int idPeriodo, int tiempoPromedio) {
        this.idTiempoRutaPeriodo = idTiempoRutaPeriodo;
        this.idRuta = idRuta;
        this.idPeriodo = idPeriodo;
        this.tiempoPromedio = tiempoPromedio;
    }

    // Constructor sin ID (para creación de nuevos registros)
    public TiempoRutaPeriodo(int idRuta, int idPeriodo, int tiempoPromedio) {
        this.idRuta = idRuta;
        this.idPeriodo = idPeriodo;
        this.tiempoPromedio = tiempoPromedio;
    }

    // Getters y Setters
    public int getIdTiempoRutaPeriodo() {
        return idTiempoRutaPeriodo;
    }

    public void setIdTiempoRutaPeriodo(int idTiempoRutaPeriodo) {
        this.idTiempoRutaPeriodo = idTiempoRutaPeriodo;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getTiempoPromedio() {
        return tiempoPromedio;
    }

    public void setTiempoPromedio(int tiempoPromedio) {
        this.tiempoPromedio = tiempoPromedio;
    }

    @Override
    public String toString() {
        return "TiempoRutaPeriodo{" +
                "idTiempoRutaPeriodo=" + idTiempoRutaPeriodo +
                ", idRuta=" + idRuta +
                ", idPeriodo=" + idPeriodo +
                ", tiempoPromedio=" + tiempoPromedio +
                '}';
    }
}