package com.wheely.model;

/**
 * Modelo de datos para la tabla Parada
 * Representa una parada de autobús en un recorrido
 */
public class Parada {
    private int idParada;
    private int idRecorrido;
    private String nombreArchivoGeojson;
    private boolean activo;

    // Constructor vacío
    public Parada() {}

    // Constructor completo
    public Parada(int idParada, int idRecorrido, String nombreArchivoGeojson, boolean activo) {
        this.idParada = idParada;
        this.idRecorrido = idRecorrido;
        this.nombreArchivoGeojson = nombreArchivoGeojson;
        this.activo = activo;
    }

    // Constructor sin ID
    public Parada(int idRecorrido, String nombreArchivoGeojson, boolean activo) {
        this.idRecorrido = idRecorrido;
        this.nombreArchivoGeojson = nombreArchivoGeojson;
        this.activo = activo;
    }

    // Getters y Setters
    public int getIdParada() {
        return idParada;
    }

    public void setIdParada(int idParada) {
        this.idParada = idParada;
    }

    public int getIdRecorrido() {
        return idRecorrido;
    }

    public void setIdRecorrido(int idRecorrido) {
        this.idRecorrido = idRecorrido;
    }

    public String getNombreArchivoGeojson() {
        return nombreArchivoGeojson;
    }

    public void setNombreArchivoGeojson(String nombreArchivoGeojson) {
        this.nombreArchivoGeojson = nombreArchivoGeojson;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Parada{" +
                "idParada=" + idParada +
                ", idRecorrido=" + idRecorrido +
                ", nombreArchivoGeojson='" + nombreArchivoGeojson + '\'' +
                ", activo=" + activo +
                '}';
    }
}