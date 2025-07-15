package com.wheely.repository;

import com.wheely.config.DatabaseConfig;
import com.wheely.model.RutaFavorita;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla RutaFavorita
 * Maneja todas las interacciones con la base de datos para rutas favoritas
 */
public class RutaFavoritaRepository {

    /**
     * Obtiene todas las rutas favoritas de la base de datos
     * @return Lista de todas las rutas favoritas
     * @throws SQLException Error en la consulta
     */
    public List<RutaFavorita> findAll() throws SQLException {
        List<RutaFavorita> favoritas = new ArrayList<>();
        String query = "SELECT idRutaFavorita, idUsuario, idRuta FROM RutaFavorita ORDER BY idUsuario, idRuta";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RutaFavorita favorita = new RutaFavorita();
                favorita.setIdRutaFavorita(rs.getInt("idRutaFavorita"));
                favorita.setIdUsuario(rs.getInt("idUsuario"));
                favorita.setIdRuta(rs.getInt("idRuta"));
                favoritas.add(favorita);
            }
        }
        return favoritas;
    }

    /**
     * Busca una ruta favorita por su ID
     * @param idRutaFavorita ID de la ruta favorita a buscar
     * @return Ruta favorita encontrada o null si no existe
     * @throws SQLException Error en la consulta
     */
    public RutaFavorita findById(int idRutaFavorita) throws SQLException {
        RutaFavorita favorita = null;
        String query = "SELECT idRutaFavorita, idUsuario, idRuta FROM RutaFavorita WHERE idRutaFavorita = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRutaFavorita);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    favorita = new RutaFavorita();
                    favorita.setIdRutaFavorita(rs.getInt("idRutaFavorita"));
                    favorita.setIdUsuario(rs.getInt("idUsuario"));
                    favorita.setIdRuta(rs.getInt("idRuta"));
                }
            }
        }
        return favorita;
    }

    /**
     * Obtiene todas las rutas favoritas de un usuario específico
     * @param idUsuario ID del usuario
     * @return Lista de rutas favoritas del usuario
     * @throws SQLException Error en la consulta
     */
    public List<RutaFavorita> findByUsuario(int idUsuario) throws SQLException {
        List<RutaFavorita> favoritas = new ArrayList<>();
        String query = "SELECT idRutaFavorita, idUsuario, idRuta FROM RutaFavorita WHERE idUsuario = ? ORDER BY idRuta";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    RutaFavorita favorita = new RutaFavorita();
                    favorita.setIdRutaFavorita(rs.getInt("idRutaFavorita"));
                    favorita.setIdUsuario(rs.getInt("idUsuario"));
                    favorita.setIdRuta(rs.getInt("idRuta"));
                    favoritas.add(favorita);
                }
            }
        }
        return favoritas;
    }

    /**
     * Obtiene todos los usuarios que tienen una ruta específica como favorita
     * @param idRuta ID de la ruta
     * @return Lista de rutas favoritas para esa ruta
     * @throws SQLException Error en la consulta
     */
    public List<RutaFavorita> findByRuta(int idRuta) throws SQLException {
        List<RutaFavorita> favoritas = new ArrayList<>();
        String query = "SELECT idRutaFavorita, idUsuario, idRuta FROM RutaFavorita WHERE idRuta = ? ORDER BY idUsuario";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRuta);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    RutaFavorita favorita = new RutaFavorita();
                    favorita.setIdRutaFavorita(rs.getInt("idRutaFavorita"));
                    favorita.setIdUsuario(rs.getInt("idUsuario"));
                    favorita.setIdRuta(rs.getInt("idRuta"));
                    favoritas.add(favorita);
                }
            }
        }
        return favoritas;
    }

    /**
     * Guarda una nueva ruta favorita en la base de datos
     * @param rutaFavorita Ruta favorita a guardar
     * @return ID de la ruta favorita creada
     * @throws SQLException Error en la inserción
     */
    public int save(RutaFavorita rutaFavorita) throws SQLException {
        String query = "INSERT INTO RutaFavorita (idUsuario, idRuta) VALUES (?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, rutaFavorita.getIdUsuario());
            stmt.setInt(2, rutaFavorita.getIdRuta());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear ruta favorita, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear ruta favorita, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Elimina una ruta favorita por su ID
     * @param idRutaFavorita ID de la ruta favorita a eliminar
     * @return true si se eliminó correctamente, false si no se encontró la ruta favorita
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idRutaFavorita) throws SQLException {
        String query = "DELETE FROM RutaFavorita WHERE idRutaFavorita = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRutaFavorita);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina una ruta favorita por usuario y ruta
     * @param idUsuario ID del usuario
     * @param idRuta ID de la ruta
     * @return true si se eliminó correctamente, false si no se encontró la combinación
     * @throws SQLException Error en la eliminación
     */
    public boolean deleteByUsuarioAndRuta(int idUsuario, int idRuta) throws SQLException {
        String query = "DELETE FROM RutaFavorita WHERE idUsuario = ? AND idRuta = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idRuta);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Verifica si una combinación usuario-ruta ya existe como favorita
     * @param idUsuario ID del usuario
     * @param idRuta ID de la ruta
     * @return true si ya existe la combinación, false en caso contrario
     * @throws SQLException Error en la consulta
     */
    public boolean existsByUsuarioAndRuta(int idUsuario, int idRuta) throws SQLException {
        String query = "SELECT 1 FROM RutaFavorita WHERE idUsuario = ? AND idRuta = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idRuta);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Busca la ruta favorita por usuario y ruta
     * @param idUsuario ID del usuario
     * @param idRuta ID de la ruta
     * @return Ruta favorita encontrada o null si no existe
     * @throws SQLException Error en la consulta
     */
    public RutaFavorita findByUsuarioAndRuta(int idUsuario, int idRuta) throws SQLException {
        RutaFavorita favorita = null;
        String query = "SELECT idRutaFavorita, idUsuario, idRuta FROM RutaFavorita WHERE idUsuario = ? AND idRuta = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idRuta);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    favorita = new RutaFavorita();
                    favorita.setIdRutaFavorita(rs.getInt("idRutaFavorita"));
                    favorita.setIdUsuario(rs.getInt("idUsuario"));
                    favorita.setIdRuta(rs.getInt("idRuta"));
                }
            }
        }
        return favorita;
    }

    /**
     * Obtiene el conteo total de rutas favoritas
     * @return Número total de rutas favoritas
     * @throws SQLException Error en la consulta
     */
    public int count() throws SQLException {
        String query = "SELECT COUNT(*) FROM RutaFavorita";

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
     * Obtiene el conteo de favoritos por usuario
     * @param idUsuario ID del usuario
     * @return Número de rutas favoritas del usuario
     * @throws SQLException Error en la consulta
     */
    public int countByUsuario(int idUsuario) throws SQLException {
        String query = "SELECT COUNT(*) FROM RutaFavorita WHERE idUsuario = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }
    }

    /**
     * Obtiene el conteo de veces que una ruta ha sido marcada como favorita
     * @param idRuta ID de la ruta
     * @return Número de usuarios que tienen esta ruta como favorita
     * @throws SQLException Error en la consulta
     */
    public int countByRuta(int idRuta) throws SQLException {
        String query = "SELECT COUNT(*) FROM RutaFavorita WHERE idRuta = ?";

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