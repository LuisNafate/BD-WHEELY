package com.wheely.model;

import java.math.BigDecimal;

/**
 * Modelo de datos para la tabla Coordenada
 * Representa una coordenada geográfica del recorrido
 */
public class Coordenada {
    private int idCoordenada;
    private int idRecorrido;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private int ordenPunto;

    // Constructor vacío
    public Coordenada() {}

    // Constructor completo
    public Coordenada(int idCoordenada, int idRecorrido, BigDecimal latitud, BigDecimal longitud, int ordenPunto) {
        this.idCoordenada = idCoordenada;
        this.idRecorrido = idRecorrido;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ordenPunto = ordenPunto;
    }

    // Constructor sin ID
    public Coordenada(int idRecorrido, BigDecimal latitud, BigDecimal longitud, int ordenPunto) {
        this.idRecorrido = idRecorrido;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ordenPunto = ordenPunto;
    }

    // Getters y Setters
    public int getIdCoordenada() {
        return idCoordenada;
    }

    public void setIdCoordenada(int idCoordenada) {
        this.idCoordenada = idCoordenada;
    }

    public int getIdRecorrido() {
        return idRecorrido;
    }

    public void setIdRecorrido(int idRecorrido) {
        this.idRecorrido = idRecorrido;
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

    public int getOrdenPunto() {
        return ordenPunto;
    }

    public void setOrdenPunto(int ordenPunto) {
        this.ordenPunto = ordenPunto;
    }

    @Override
    public String toString() {
        return "Coordenada{" +
                "idCoordenada=" + idCoordenada +
                ", idRecorrido=" + idRecorrido +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", ordenPunto=" + ordenPunto +
                '}';
    }
}