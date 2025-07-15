package com.wheely.repository;

import com.wheely.config.DatabaseConfig;
import com.wheely.model.Parada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla Parada
 * Maneja todas las interacciones con la base de datos para paradas
 */
public class ParadaRepository {

    /**
     * Obtiene todas las paradas de la base de datos
     * @return Lista de todas las paradas
     * @throws SQLException Error en la consulta
     */
    public List<Parada> findAll() throws SQLException {
        List<Parada> paradas = new ArrayList<>();
        String query = "SELECT idParada, idRecorrido, nombre_archivo_geojson, activo FROM Parada ORDER BY idRecorrido, nombre_archivo_geojson";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Parada parada = new Parada();
                parada.setIdParada(rs.getInt("idParada"));
                parada.setIdRecorrido(rs.getInt("idRecorrido"));
                parada.setNombreArchivoGeojson(rs.getString("nombre_archivo_geojson"));
                parada.setActivo(rs.getBoolean("activo"));
                paradas.add(parada);
            }
        }
        return paradas;
    }

    /**
     * Busca una parada por su ID
     * @param idParada ID de la parada a buscar
     * @return Parada encontrada o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Parada findById(int idParada) throws SQLException {
        Parada parada = null;
        String query = "SELECT idParada, idRecorrido, nombre_archivo_geojson, activo FROM Parada WHERE idParada = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idParada);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    parada = new Parada();
                    parada.setIdParada(rs.getInt("idParada"));
                    parada.setIdRecorrido(rs.getInt("idRecorrido"));
                    parada.setNombreArchivoGeojson(rs.getString("nombre_archivo_geojson"));
                    parada.setActivo(rs.getBoolean("activo"));
                }
            }
        }
        return parada;
    }

    /**
     * Obtiene todas las paradas de un recorrido específico
     * @param idRecorrido ID del recorrido
     * @return Lista de paradas del recorrido
     * @throws SQLException Error en la consulta
     */
    public List<Parada> findByRecorrido(int idRecorrido) throws SQLException {
        List<Parada> paradas = new ArrayList<>();
        String query = "SELECT idParada, idRecorrido, nombre_archivo_geojson, activo FROM Parada WHERE idRecorrido = ? ORDER BY nombre_archivo_geojson";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRecorrido);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Parada parada = new Parada();
                    parada.setIdParada(rs.getInt("idParada"));
                    parada.setIdRecorrido(rs.getInt("idRecorrido"));
                    parada.setNombreArchivoGeojson(rs.getString("nombre_archivo_geojson"));
                    parada.setActivo(rs.getBoolean("activo"));
                    paradas.add(parada);
                }
            }
        }
        return paradas;
    }

    /**
     * Obtiene solo las paradas activas
     * @return Lista de paradas activas
     * @throws SQLException Error en la consulta
     */
    public List<Parada> findActivas() throws SQLException {
        List<Parada> paradas = new ArrayList<>();
        String query = "SELECT idParada, idRecorrido, nombre_archivo_geojson, activo FROM Parada WHERE activo = 1 ORDER BY idRecorrido, nombre_archivo_geojson";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Parada parada = new Parada();
                parada.setIdParada(rs.getInt("idParada"));
                parada.setIdRecorrido(rs.getInt("idRecorrido"));
                parada.setNombreArchivoGeojson(rs.getString("nombre_archivo_geojson"));
                parada.setActivo(rs.getBoolean("activo"));
                paradas.add(parada);
            }
        }
        return paradas;
    }

    /**
     * Obtiene las paradas activas de un recorrido específico
     * @param idRecorrido ID del recorrido
     * @return Lista de paradas activas del recorrido
     * @throws SQLException Error en la consulta
     */
    public List<Parada> findActivasByRecorrido(int idRecorrido) throws SQLException {
        List<Parada> paradas = new ArrayList<>();
        String query = "SELECT idParada, idRecorrido, nombre_archivo_geojson, activo FROM Parada WHERE idRecorrido = ? AND activo = 1 ORDER BY nombre_archivo_geojson";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRecorrido);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Parada parada = new Parada();
                    parada.setIdParada(rs.getInt("idParada"));
                    parada.setIdRecorrido(rs.getInt("idRecorrido"));
                    parada.setNombreArchivoGeojson(rs.getString("nombre_archivo_geojson"));
                    parada.setActivo(rs.getBoolean("activo"));
                    paradas.add(parada);
                }
            }
        }
        return paradas;
    }

    /**
     * Guarda una nueva parada en la base de datos
     * @param parada Parada a guardar
     * @return ID de la parada creada
     * @throws SQLException Error en la inserción
     */
    public int save(Parada parada) throws SQLException {
        String query = "INSERT INTO Parada (idRecorrido, nombre_archivo_geojson, activo) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, parada.getIdRecorrido());
            stmt.setString(2, parada.getNombreArchivoGeojson());
            stmt.setBoolean(3, parada.isActivo());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear parada, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear parada, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Actualiza una parada existente
     * @param parada Parada con los datos actualizados
     * @return true si se actualizó correctamente, false si no se encontró la parada
     * @throws SQLException Error en la actualización
     */
    public boolean update(Parada parada) throws SQLException {
        String query = "UPDATE Parada SET idRecorrido = ?, nombre_archivo_geojson = ?, activo = ? WHERE idParada = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, parada.getIdRecorrido());
            stmt.setString(2, parada.getNombreArchivoGeojson());
            stmt.setBoolean(3, parada.isActivo());
            stmt.setInt(4, parada.getIdParada());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina una parada por su ID
     * @param idParada ID de la parada a eliminar
     * @return true si se eliminó correctamente, false si no se encontró la parada
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idParada) throws SQLException {
        String query = "DELETE FROM Parada WHERE idParada = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idParada);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Activa o desactiva una parada
     * @param idParada ID de la parada
     * @param activo Estado a establecer
     * @return true si se actualizó correctamente
     * @throws SQLException Error en la actualización
     */
    public boolean updateEstado(int idParada, boolean activo) throws SQLException {
        String query = "UPDATE Parada SET activo = ? WHERE idParada = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBoolean(1, activo);
            stmt.setInt(2, idParada);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Busca paradas por nombre de archivo (búsqueda parcial)
     * @param nombreArchivo Nombre o parte del nombre del archivo a buscar
     * @return Lista de paradas que coinciden con la búsqueda
     * @throws SQLException Error en la consulta
     */
    public List<Parada> findByNombreArchivo(String nombreArchivo) throws SQLException {
        List<Parada> paradas = new ArrayList<>();
        String query = "SELECT idParada, idRecorrido, nombre_archivo_geojson, activo FROM Parada WHERE nombre_archivo_geojson LIKE ? ORDER BY idRecorrido, nombre_archivo_geojson";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + nombreArchivo + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Parada parada = new Parada();
                    parada.setIdParada(rs.getInt("idParada"));
                    parada.setIdRecorrido(rs.getInt("idRecorrido"));
                    parada.setNombreArchivoGeojson(rs.getString("nombre_archivo_geojson"));
                    parada.setActivo(rs.getBoolean("activo"));
                    paradas.add(parada);
                }
            }
        }
        return paradas;
    }

    /**
     * Obtiene el conteo total de paradas
     * @return Número total de paradas
     * @throws SQLException Error en la consulta
     */
    public int count() throws SQLException {
        String query = "SELECT COUNT(*) FROM Parada";

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
     * Obtiene el conteo de paradas activas
     * @return Número de paradas activas
     * @throws SQLException Error en la consulta
     */
    public int countActivas() throws SQLException {
        String query = "SELECT COUNT(*) FROM Parada WHERE activo = 1";

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
     * Obtiene el conteo de paradas por recorrido
     * @param idRecorrido ID del recorrido
     * @return Número de paradas del recorrido
     * @throws SQLException Error en la consulta
     */
    public int countByRecorrido(int idRecorrido) throws SQLException {
        String query = "SELECT COUNT(*) FROM Parada WHERE idRecorrido = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRecorrido);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }
    }

    /**
     * Verifica si existe una parada con el mismo nombre de archivo
     * @param nombreArchivo Nombre del archivo a verificar
     * @return true si ya existe una parada con ese nombre de archivo
     * @throws SQLException Error en la consulta
     */
    public boolean existsByNombreArchivo(String nombreArchivo) throws SQLException {
        String query = "SELECT 1 FROM Parada WHERE nombre_archivo_geojson = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreArchivo);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}