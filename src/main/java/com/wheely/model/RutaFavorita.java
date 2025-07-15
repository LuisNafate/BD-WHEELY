package com.wheely.model;

/**
 * Modelo de datos para la tabla RutaFavorita
 * Representa las rutas favoritas de un usuario
 */
public class RutaFavorita {
    private int idRutaFavorita;
    private int idUsuario;
    private int idRuta;

    // Constructor vacío
    public RutaFavorita() {}

    // Constructor completo
    public RutaFavorita(int idRutaFavorita, int idUsuario, int idRuta) {
        this.idRutaFavorita = idRutaFavorita;
        this.idUsuario = idUsuario;
        this.idRuta = idRuta;
    }

    // Constructor sin ID
    public RutaFavorita(int idUsuario, int idRuta) {
        this.idUsuario = idUsuario;
        this.idRuta = idRuta;
    }

    // Getters y Setters
    public int getIdRutaFavorita() {
        return idRutaFavorita;
    }

    public void setIdRutaFavorita(int idRutaFavorita) {
        this.idRutaFavorita = idRutaFavorita;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    @Override
    public String toString() {
        return "RutaFavorita{" +
                "idRutaFavorita=" + idRutaFavorita +
                ", idUsuario=" + idUsuario +
                ", idRuta=" + idRuta +
                '}';
    }
}