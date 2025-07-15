package com.wheely.model;

import java.math.BigDecimal;

/**
 * Modelo de datos para la tabla CoordenadaParada
 * Representa las coordenadas geográficas de una parada específica
 */
public class CoordenadaParada {
    private int idCoordenadaParada;
    private int idParada;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private int ordenParada;

    // Constructor vacío
    public CoordenadaParada() {}

    // Constructor completo
    public CoordenadaParada(int idCoordenadaParada, int idParada, BigDecimal latitud, BigDecimal longitud, int ordenParada) {
        this.idCoordenadaParada = idCoordenadaParada;
        this.idParada = idParada;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ordenParada = ordenParada;
    }

    // Constructor sin ID
    public CoordenadaParada(int idParada, BigDecimal latitud, BigDecimal longitud, int ordenParada) {
        this.idParada = idParada;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ordenParada = ordenParada;
    }

    // Getters y Setters
    public int getIdCoordenadaParada() {
        return idCoordenadaParada;
    }

    public void setIdCoordenadaParada(int idCoordenadaParada) {
        this.idCoordenadaParada = idCoordenadaParada;
    }

    public int getIdParada() {
        return idParada;
    }

    public void setIdParada(int idParada) {
        this.idParada = idParada;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public int getOrdenParada() {
        return ordenParada;
    }

    public void setOrdenParada(int ordenParada) {
        this.ordenParada = ordenParada;
    }

    @Override
    public String toString() {
        return "CoordenadaParada{" +
                "idCoordenadaParada=" + idCoordenadaParada +
                ", idParada=" + idParada +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", ordenParada=" + ordenParada +
                '}';
    }
}