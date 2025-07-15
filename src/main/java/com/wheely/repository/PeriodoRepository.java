package com.wheely.repository;

import com.wheely.config.DatabaseConfig;
import com.wheely.model.Periodo;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones de base de datos de la entidad Periodo
 * Maneja todas las consultas relacionadas con los periodos del día
 */
public class PeriodoRepository {

    /**
     * Obtiene todos los periodos de la base de datos
     * @return Lista de todos los periodos
     * @throws SQLException Si hay error en la consulta
     */
    public List<Periodo> findAll() throws SQLException {
        List<Periodo> periodos = new ArrayList<>();
        String sql = "SELECT idPeriodo, nombre_periodo, hora_inicio, hora_fin, descripcion FROM Periodo ORDER BY hora_inicio";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                periodos.add(mapResultSetToPeriodo(rs));
            }
        }
        return periodos;
    }

    /**
     * Busca un periodo por su ID
     * @param id ID del periodo a buscar
     * @return Periodo encontrado o null si no existe
     * @throws SQLException Si hay error en la consulta
     */
    public Periodo findById(int id) throws SQLException {
        String sql = "SELECT idPeriodo, nombre_periodo, hora_inicio, hora_fin, descripcion FROM Periodo WHERE idPeriodo = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPeriodo(rs);
                }
            }
        }
        return null;
    }

    /**
     * Busca un periodo por su nombre
     * @param nombrePeriodo Nombre del periodo a buscar
     * @return Periodo encontrado o null si no existe
     * @throws SQLException Si hay error en la consulta
     */
    public Periodo findByNombre(String nombrePeriodo) throws SQLException {
        String sql = "SELECT idPeriodo, nombre_periodo, hora_inicio, hora_fin, descripcion FROM Periodo WHERE nombre_periodo = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombrePeriodo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPeriodo(rs);
                }
            }
        }
        return null;
    }

    /**
     * Guarda un nuevo periodo en la base de datos
     * @param periodo Periodo a guardar
     * @return ID del periodo creado
     * @throws SQLException Si hay error en la consulta
     */
    public int save(Periodo periodo) throws SQLException {
        String sql = "INSERT INTO Periodo (nombre_periodo, hora_inicio, hora_fin, descripcion) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, periodo.getNombrePeriodo());
            stmt.setTime(2, Time.valueOf(periodo.getHoraInicio()));
            stmt.setTime(3, Time.valueOf(periodo.getHoraFin()));
            stmt.setString(4, periodo.getDescripcion());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        throw new SQLException("Error al crear periodo, no se obtuvo ID");
    }

    /**
     * Actualiza un periodo existente
     * @param periodo Periodo con los datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException Si hay error en la consulta
     */
    public boolean update(Periodo periodo) throws SQLException {
        String sql = "UPDATE Periodo SET nombre_periodo = ?, hora_inicio = ?, hora_fin = ?, descripcion = ? WHERE idPeriodo = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, periodo.getNombrePeriodo());
            stmt.setTime(2, Time.valueOf(periodo.getHoraInicio()));
            stmt.setTime(3, Time.valueOf(periodo.getHoraFin()));
            stmt.setString(4, periodo.getDescripcion());
            stmt.setInt(5, periodo.getIdPeriodo());

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un periodo de la base de datos
     * @param id ID del periodo a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException Si hay error en la consulta
     */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Periodo WHERE idPeriodo = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Verifica si existe un periodo con el nombre dado
     * @param nombrePeriodo Nombre del periodo a verificar
     * @return true si existe un periodo con ese nombre
     * @throws SQLException Si hay error en la consulta
     */
    public boolean nombreExists(String nombrePeriodo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Periodo WHERE nombre_periodo = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombrePeriodo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    /**
     * Obtiene el periodo actual basado en la hora actual del sistema
     * @return Periodo actual o null si no se encuentra
     * @throws SQLException Si hay error en la consulta
     */
    public Periodo findPeriodoActual() throws SQLException {
        String sql = "SELECT idPeriodo, nombre_periodo, hora_inicio, hora_fin, descripcion FROM Periodo WHERE " +
                "(hora_inicio <= hora_fin AND CURTIME() BETWEEN hora_inicio AND hora_fin) OR " +
                "(hora_inicio > hora_fin AND (CURTIME() >= hora_inicio OR CURTIME() <= hora_fin))";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return mapResultSetToPeriodo(rs);
            }
        }
        return null;
    }

    /**
     * Mapea un ResultSet a un objeto Periodo
     * @param rs ResultSet con los datos del periodo
     * @return Objeto Periodo mapeado
     * @throws SQLException Si hay error al acceder a los datos
     */
    private Periodo mapResultSetToPeriodo(ResultSet rs) throws SQLException {
        return new Periodo(
                rs.getInt("idPeriodo"),
                rs.getString("nombre_periodo"),
                rs.getTime("hora_inicio").toLocalTime(),
                rs.getTime("hora_fin").toLocalTime(),
                rs.getString("descripcion")
        );
    }
}