package com.wheely.repository;

import com.wheely.config.DatabaseConfig;
import com.wheely.model.Recorrido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla Recorrido
 * Maneja todas las interacciones con la base de datos para recorridos
 */
public class RecorridoRepository {

    /**
     * Obtiene todos los recorridos de la base de datos
     * @return Lista de todos los recorridos
     * @throws SQLException Error en la consulta
     */
    public List<Recorrido> findAll() throws SQLException {
        List<Recorrido> recorridos = new ArrayList<>();
        String query = "SELECT idRecorrido, idRuta, nombre_archivo_geojson, activo FROM Recorrido ORDER BY idRuta, nombre_archivo_geojson";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Recorrido recorrido = new Recorrido();
                recorrido.setIdRecorrido(rs.getInt("idRecorrido"));
                recorrido.setIdRuta(rs.getInt("idRuta"));
                recorrido.setNombreArchivoGeojson(rs.getString("nombre_archivo_geojson"));
                recorrido.setActivo(rs.getBoolean("activo"));
                recorridos.add(recorrido);
            }
        }
        return recorridos;
    }

    /**
     * Busca un recorrido por su ID
     * @param idRecorrido ID del recorrido a buscar
     * @return Recorrido encontrado o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Recorrido findById(int idRecorrido) throws SQLException {
        Recorrido recorrido = null;
        String query = "SELECT idRecorrido, idRuta, nombre_archivo_geojson, activo FROM Recorrido WHERE idRecorrido = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRecorrido);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    recorrido = new Recorrido();
                    recorrido.setIdRecorrido(rs.getInt("idRecorrido"));
                    recorrido.setIdRuta(rs.getInt("idRuta"));
                    recorrido.setNombreArchivoGeojson(rs.getString("nombre_archivo_geojson"));
                    recorrido.setActivo(rs.getBoolean("activo"));
                }
            }
        }
        return recorrido;
    }

    /**
     * Obtiene todos los recorridos de una ruta específica
     * @param idRuta ID de la ruta
     * @return Lista de recorridos de la ruta
     * @throws SQLException Error en la consulta
     */
    public List<Recorrido> findByRuta(int idRuta) throws SQLException {
        List<Recorrido> recorridos = new ArrayList<>();
        String query = "SELECT idRecorrido, idRuta, nombre_archivo_geojson, activo FROM Recorrido WHERE idRuta = ? ORDER BY nombre_archivo_geojson";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRuta);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Recorrido recorrido = new Recorrido();
                    recorrido.setIdRecorrido(rs.getInt("idRecorrido"));
                    recorrido.setIdRuta(rs.getInt("idRuta"));
                    recorrido.setNombreArchivoGeojson(rs.getString("nombre_archivo_geojson"));
                    recorrido.setActivo(rs.getBoolean("activo"));
                    recorridos.add(recorrido);
                }
            }
        }
        return recorridos;
    }

    /**
     * Obtiene solo los recorridos activos
     * @return Lista de recorridos activos
     * @throws SQLException Error en la consulta
     */
    public List<Recorrido> findActivos() throws SQLException {
        List<Recorrido> recorridos = new ArrayList<>();
        String query = "SELECT idRecorrido, idRuta, nombre_archivo_geojson, activo FROM Recorrido WHERE activo = 1 ORDER BY idRuta, nombre_archivo_geojson";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Recorrido recorrido = new Recorrido();
                recorrido.setIdRecorrido(rs.getInt("idRecorrido"));
                recorrido.setIdRuta(rs.getInt("idRuta"));
                recorrido.setNombreArchivoGeojson(rs.getString("nombre_archivo_geojson"));
                recorrido.setActivo(rs.getBoolean("activo"));
                recorridos.add(recorrido);
            }
        }
        return recorridos;
    }

    /**
     * Obtiene los recorridos activos de una ruta específica
     * @param idRuta ID de la ruta
     * @return Lista de recorridos activos de la ruta
     * @throws SQLException Error en la consulta
     */
    public List<Recorrido> findActivosByRuta(int idRuta) throws SQLException {
        List<Recorrido> recorridos = new ArrayList<>();
        String query = "SELECT idRecorrido, idRuta, nombre_archivo_geojson, activo FROM Recorrido WHERE idRuta = ? AND activo = 1 ORDER BY nombre_archivo_geojson";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRuta);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Recorrido recorrido = new Recorrido();
                    recorrido.setIdRecorrido(rs.getInt("idRecorrido"));
                    recorrido.setIdRuta(rs.getInt("idRuta"));
                    recorrido.setNombreArchivoGeojson(rs.getString("nombre_archivo_geojson"));
                    recorrido.setActivo(rs.getBoolean("activo"));
                    recorridos.add(recorrido);
                }
            }
        }
        return recorridos;
    }

    /**
     * Guarda un nuevo recorrido en la base de datos
     * @param recorrido Recorrido a guardar
     * @return ID del recorrido creado
     * @throws SQLException Error en la inserción
     */
    public int save(Recorrido recorrido) throws SQLException {
        String query = "INSERT INTO Recorrido (idRuta, nombre_archivo_geojson, activo) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, recorrido.getIdRuta());
            stmt.setString(2, recorrido.getNombreArchivoGeojson());
            stmt.setBoolean(3, recorrido.isActivo());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear recorrido, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear recorrido, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Actualiza un recorrido existente
     * @param recorrido Recorrido con los datos actualizados
     * @return true si se actualizó correctamente, false si no se encontró el recorrido
     * @throws SQLException Error en la actualización
     */
    public boolean update(Recorrido recorrido) throws SQLException {
        String query = "UPDATE Recorrido SET idRuta = ?, nombre_archivo_geojson = ?, activo = ? WHERE idRecorrido = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, recorrido.getIdRuta());
            stmt.setString(2, recorrido.getNombreArchivoGeojson());
            stmt.setBoolean(3, recorrido.isActivo());
            stmt.setInt(4, recorrido.getIdRecorrido());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina un recorrido por su ID
     * @param idRecorrido ID del recorrido a eliminar
     * @return true si se eliminó correctamente, false si no se encontró el recorrido
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idRecorrido) throws SQLException {
        String query = "DELETE FROM Recorrido WHERE idRecorrido = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRecorrido);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Activa o desactiva un recorrido
     * @param idRecorrido ID del recorrido
     * @param activo Estado a establecer
     * @return true si se actualizó correctamente
     * @throws SQLException Error en la actualización
     */
    public boolean updateEstado(int idRecorrido, boolean activo) throws SQLException {
        String query = "UPDATE Recorrido SET activo = ? WHERE idRecorrido = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBoolean(1, activo);
            stmt.setInt(2, idRecorrido);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Busca recorridos por nombre de archivo (búsqueda parcial)
     * @param nombreArchivo Nombre o parte del nombre del archivo a buscar
     * @return Lista de recorridos que coinciden con la búsqueda
     * @throws SQLException Error en la consulta
     */
    public List<Recorrido> findByNombreArchivo(String nombreArchivo) throws SQLException {
        List<Recorrido> recorridos = new ArrayList<>();
        String query = "SELECT idRecorrido, idRuta, nombre_archivo_geojson, activo FROM Recorrido WHERE nombre_archivo_geojson LIKE ? ORDER BY idRuta, nombre_archivo_geojson";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + nombreArchivo + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Recorrido recorrido = new Recorrido();
                    recorrido.setIdRecorrido(rs.getInt("idRecorrido"));
                    recorrido.setIdRuta(rs.getInt("idRuta"));
                    recorrido.setNombreArchivoGeojson(rs.getString("nombre_archivo_geojson"));
                    recorrido.setActivo(rs.getBoolean("activo"));
                    recorridos.add(recorrido);
                }
            }
        }
        return recorridos;
    }

    /**
     * Verifica si existe un recorrido con el mismo nombre de archivo
     * @param nombreArchivo Nombre del archivo a verificar
     * @return true si ya existe un recorrido con ese nombre de archivo
     * @throws SQLException Error en la consulta
     */
    public boolean existsByNombreArchivo(String nombreArchivo) throws SQLException {
        String query = "SELECT 1 FROM Recorrido WHERE nombre_archivo_geojson = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreArchivo);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Obtiene el conteo total de recorridos
     * @return Número total de recorridos
     * @throws SQLException Error en la consulta
     */
    public int count() throws SQLException {
        String query = "SELECT COUNT(*) FROM Recorrido";

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
     * Obtiene el conteo de recorridos activos
     * @return Número de recorridos activos
     * @throws SQLException Error en la consulta
     */
    public int countActivos() throws SQLException {
        String query = "SELECT COUNT(*) FROM Recorrido WHERE activo = 1";

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
     * Obtiene el conteo de recorridos por ruta
     * @param idRuta ID de la ruta
     * @return Número de recorridos de la ruta
     * @throws SQLException Error en la consulta
     */
    public int countByRuta(int idRuta) throws SQLException {
        String query = "SELECT COUNT(*) FROM Recorrido WHERE idRuta = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRuta);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }
    }
}