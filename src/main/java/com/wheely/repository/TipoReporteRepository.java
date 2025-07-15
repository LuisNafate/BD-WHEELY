package com.wheely.repository;

import com.wheely.config.DatabaseConfig;
import com.wheely.model.TipoReporte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla TipoReporte
 * Maneja todas las interacciones con la base de datos para tipos de reporte
 */
public class TipoReporteRepository {

    /**
     * Obtiene todos los tipos de reporte de la base de datos
     * @return Lista de todos los tipos de reporte
     * @throws SQLException Error en la consulta
     */
    public List<TipoReporte> findAll() throws SQLException {
        List<TipoReporte> tiposReporte = new ArrayList<>();
        String query = "SELECT idTipoReporte, nombre_tipo, descripcion FROM TipoReporte ORDER BY nombre_tipo";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TipoReporte tipoReporte = new TipoReporte();
                tipoReporte.setIdTipoReporte(rs.getInt("idTipoReporte"));
                tipoReporte.setNombreTipo(rs.getString("nombre_tipo"));
                tipoReporte.setDescripcion(rs.getString("descripcion"));
                tiposReporte.add(tipoReporte);
            }
        }
        return tiposReporte;
    }

    /**
     * Busca un tipo de reporte por su ID
     * @param idTipoReporte ID del tipo de reporte a buscar
     * @return Tipo de reporte encontrado o null si no existe
     * @throws SQLException Error en la consulta
     */
    public TipoReporte findById(int idTipoReporte) throws SQLException {
        TipoReporte tipoReporte = null;
        String query = "SELECT idTipoReporte, nombre_tipo, descripcion FROM TipoReporte WHERE idTipoReporte = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idTipoReporte);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tipoReporte = new TipoReporte();
                    tipoReporte.setIdTipoReporte(rs.getInt("idTipoReporte"));
                    tipoReporte.setNombreTipo(rs.getString("nombre_tipo"));
                    tipoReporte.setDescripcion(rs.getString("descripcion"));
                }
            }
        }
        return tipoReporte;
    }

    /**
     * Guarda un nuevo tipo de reporte en la base de datos
     * @param tipoReporte Tipo de reporte a guardar
     * @return ID del tipo de reporte creado
     * @throws SQLException Error en la inserción
     */
    public int save(TipoReporte tipoReporte) throws SQLException {
        String query = "INSERT INTO TipoReporte (nombre_tipo, descripcion) VALUES (?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, tipoReporte.getNombreTipo());
            stmt.setString(2, tipoReporte.getDescripcion());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear tipo de reporte, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear tipo de reporte, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Actualiza un tipo de reporte existente
     * @param tipoReporte Tipo de reporte con los datos actualizados
     * @return true si se actualizó correctamente, false si no se encontró el tipo de reporte
     * @throws SQLException Error en la actualización
     */
    public boolean update(TipoReporte tipoReporte) throws SQLException {
        String query = "UPDATE TipoReporte SET nombre_tipo = ?, descripcion = ? WHERE idTipoReporte = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, tipoReporte.getNombreTipo());
            stmt.setString(2, tipoReporte.getDescripcion());
            stmt.setInt(3, tipoReporte.getIdTipoReporte());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina un tipo de reporte por su ID
     * @param idTipoReporte ID del tipo de reporte a eliminar
     * @return true si se eliminó correctamente, false si no se encontró el tipo de reporte
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idTipoReporte) throws SQLException {
        String query = "DELETE FROM TipoReporte WHERE idTipoReporte = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idTipoReporte);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Busca tipos de reporte por nombre (búsqueda parcial)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de tipos de reporte que coinciden con la búsqueda
     * @throws SQLException Error en la consulta
     */
    public List<TipoReporte> findByNombre(String nombre) throws SQLException {
        List<TipoReporte> tiposReporte = new ArrayList<>();
        String query = "SELECT idTipoReporte, nombre_tipo, descripcion FROM TipoReporte WHERE nombre_tipo LIKE ? ORDER BY nombre_tipo";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + nombre + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TipoReporte tipoReporte = new TipoReporte();
                    tipoReporte.setIdTipoReporte(rs.getInt("idTipoReporte"));
                    tipoReporte.setNombreTipo(rs.getString("nombre_tipo"));
                    tipoReporte.setDescripcion(rs.getString("descripcion"));
                    tiposReporte.add(tipoReporte);
                }
            }
        }
        return tiposReporte;
    }

    /**
     * Obtiene el conteo total de tipos de reporte
     * @return Número total de tipos de reporte
     * @throws SQLException Error en la consulta
     */
    public int count() throws SQLException {
        String query = "SELECT COUNT(*) FROM TipoReporte";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    /**
     * Verifica si existe un tipo de reporte con el mismo nombre
     * @param nombreTipo Nombre del tipo de reporte a verificar
     * @return true si ya existe un tipo de reporte con ese nombre
     * @throws SQLException Error en la consulta
     */
    public boolean existsByNombre(String nombreTipo) throws SQLException {
        String query = "SELECT 1 FROM TipoReporte WHERE nombre_tipo = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreTipo);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Verifica si existe un tipo de reporte con el mismo nombre, excluyendo un ID específico
     * Útil para validaciones en actualizaciones
     * @param nombreTipo Nombre del tipo de reporte a verificar
     * @param excludeId ID del tipo de reporte a excluir de la verificación
     * @return true si ya existe un tipo de reporte con ese nombre en otro registro
     * @throws SQLException Error en la consulta
     */
    public boolean existsByNombreExcludingId(String nombreTipo, int excludeId) throws SQLException {
        String query = "SELECT 1 FROM TipoReporte WHERE nombre_tipo = ? AND idTipoReporte != ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreTipo);
            stmt.setInt(2, excludeId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}