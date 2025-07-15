package com.wheely.model;

import java.time.LocalTime;

/**
 * Modelo de datos para la tabla Periodo
 * Representa los diferentes periodos del día (Mañana, Tarde, Noche)
 * utilizados para calcular tiempos promedio de rutas
 */
public class Periodo {
    private int idPeriodo;
    private String nombrePeriodo;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String descripcion;

    // Constructor vacío requerido para Jackson
    public Periodo() {}

    // Constructor completo
    public Periodo(int idPeriodo, String nombrePeriodo, LocalTime horaInicio,
                   LocalTime horaFin, String descripcion) {
        this.idPeriodo = idPeriodo;
        this.nombrePeriodo = nombrePeriodo;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.descripcion = descripcion;
    }

    // Constructor sin ID (para creación de nuevos periodos)
    public Periodo(String nombrePeriodo, LocalTime horaInicio, LocalTime horaFin, String descripcion) {
        this.nombrePeriodo = nombrePeriodo;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Periodo{" +
                "idPeriodo=" + idPeriodo +
                ", nombrePeriodo='" + nombrePeriodo + '\'' +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}