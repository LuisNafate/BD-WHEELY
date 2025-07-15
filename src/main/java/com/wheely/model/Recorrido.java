package com.wheely.model;

/**
 * Modelo de datos para la tabla Recorrido
 * Representa el recorrido específico de una ruta
 */
public class Recorrido {
    private int idRecorrido;
    private int idRuta;
    private String nombreArchivoGeojson;
    private boolean activo;

    // Constructor vacío
    public Recorrido() {}

    // Constructor completo
    public Recorrido(int idRecorrido, int idRuta, String nombreArchivoGeojson, boolean activo) {
        this.idRecorrido = idRecorrido;
        this.idRuta = idRuta;
        this.nombreArchivoGeojson = nombreArchivoGeojson;
        this.activo = activo;
    }

    // Constructor sin ID
    public Recorrido(int idRuta, String nombreArchivoGeojson, boolean activo) {
        this.idRuta = idRuta;
        this.nombreArchivoGeojson = nombreArchivoGeojson;
        this.activo = activo;
    }

    // Getters y Setters
    public int getIdRecorrido() {
        return idRecorrido;
    }

    public void setIdRecorrido(int idRecorrido) {
        this.idRecorrido = idRecorrido;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
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
        return "Recorrido{" +
                "idRecorrido=" + idRecorrido +
                ", idRuta=" + idRuta +
                ", nombreArchivoGeojson='" + nombreArchivoGeojson + '\'' +
                ", activo=" + activo +
                '}';
    }
}