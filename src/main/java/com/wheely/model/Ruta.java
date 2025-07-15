package com.wheely.model;

/**
 * Modelo de datos para la tabla Ruta
 * Representa una ruta de transporte público
 * Actualizado para nueva estructura sin tiempo_promedio
 */
public class Ruta {
    private int idRuta;
    private String nombreRuta;
    private String origen;
    private String destino;

    // Constructor vacío requerido para Jackson
    public Ruta() {}

    // Constructor completo
    public Ruta(int idRuta, String nombreRuta, String origen, String destino) {
        this.idRuta = idRuta;
        this.nombreRuta = nombreRuta;
        this.origen = origen;
        this.destino = destino;
    }

    // Constructor sin ID (para creación)
    public Ruta(String nombreRuta, String origen, String destino) {
        this.nombreRuta = nombreRuta;
        this.origen = origen;
        this.destino = destino;
    }

    // Getters y Setters
    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    @Override
    public String toString() {
        return "Ruta{" +
                "idRuta=" + idRuta +
                ", nombreRuta='" + nombreRuta + '\'' +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                '}';
    }
}