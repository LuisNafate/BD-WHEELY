package com.wheely.repository;

import com.wheely.config.DatabaseConfig;
import com.wheely.model.Ruta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla Ruta
 * Maneja todas las interacciones con la base de datos para rutas
 * Actualizado para nueva estructura sin tiempo_promedio
 */
public class RutaRepository {

    /**
     * Obtiene todas las rutas de la base de datos
     * @return Lista de todas las rutas
     * @throws SQLException Error en la consulta
     */
    public List<Ruta> findAll() throws SQLException {
        List<Ruta> rutas = new ArrayList<>();
        String query = "SELECT idRuta, nombre_ruta, origen, destino FROM Ruta ORDER BY nombre_ruta";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ruta ruta = new Ruta();
                ruta.setIdRuta(rs.getInt("idRuta"));
                ruta.setNombreRuta(rs.getString("nombre_ruta"));
                ruta.setOrigen(rs.getString("origen"));
                ruta.setDestino(rs.getString("destino"));
                rutas.add(ruta);
            }
        }
        return rutas;
    }

    /**
     * Busca una ruta por su ID
     * @param idRuta ID de la ruta a buscar
     * @return Ruta encontrada o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Ruta findById(int idRuta) throws SQLException {
        Ruta ruta = null;
        String query = "SELECT idRuta, nombre_ruta, origen, destino FROM Ruta WHERE idRuta = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRuta);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ruta = new Ruta();
                    ruta.setIdRuta(rs.getInt("idRuta"));
                    ruta.setNombreRuta(rs.getString("nombre_ruta"));
                    ruta.setOrigen(rs.getString("origen"));
                    ruta.setDestino(rs.getString("destino"));
                }
            }
        }
        return ruta;
    }

    /**
     * Guarda una nueva ruta en la base de datos
     * @param ruta Ruta a guardar
     * @return ID de la ruta creada
     * @throws SQLException Error en la inserción
     */
    public int save(Ruta ruta) throws SQLException {
        String query = "INSERT INTO Ruta (nombre_ruta, origen, destino) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, ruta.getNombreRuta());
            stmt.setString(2, ruta.getOrigen());
            stmt.setString(3, ruta.getDestino());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear ruta, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear ruta, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Actualiza una ruta existente
     * @param ruta Ruta con los datos actualizados
     * @return true si se actualizó correctamente, false si no se encontró la ruta
     * @throws SQLException Error en la actualización
     */
    public boolean update(Ruta ruta) throws SQLException {
        String query = "UPDATE Ruta SET nombre_ruta = ?, origen = ?, destino = ? WHERE idRuta = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ruta.getNombreRuta());
            stmt.setString(2, ruta.getOrigen());
            stmt.setString(3, ruta.getDestino());
            stmt.setInt(4, ruta.getIdRuta());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina una ruta por su ID
     * @param idRuta ID de la ruta a eliminar
     * @return true si se eliminó correctamente, false si no se encontró la ruta
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idRuta) throws SQLException {
        String query = "DELETE FROM Ruta WHERE idRuta = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRuta);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Busca rutas por nombre (búsqueda parcial)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de rutas que coinciden con la búsqueda
     * @throws SQLException Error en la consulta
     */
    public List<Ruta> findByNombre(String nombre) throws SQLException {
        List<Ruta> rutas = new ArrayList<>();
        String query = "SELECT idRuta, nombre_ruta, origen, destino FROM Ruta WHERE nombre_ruta LIKE ? ORDER BY nombre_ruta";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + nombre + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ruta ruta = new Ruta();
                    ruta.setIdRuta(rs.getInt("idRuta"));
                    ruta.setNombreRuta(rs.getString("nombre_ruta"));
                    ruta.setOrigen(rs.getString("origen"));
                    ruta.setDestino(rs.getString("destino"));
                    rutas.add(ruta);
                }
            }
        }
        return rutas;
    }

    /**
     * Busca rutas por origen (búsqueda parcial)
     * @param origen Origen o parte del origen a buscar
     * @return Lista de rutas que coinciden con la búsqueda
     * @throws SQLException Error en la consulta
     */
    public List<Ruta> findByOrigen(String origen) throws SQLException {
        List<Ruta> rutas = new ArrayList<>();
        String query = "SELECT idRuta, nombre_ruta, origen, destino FROM Ruta WHERE origen LIKE ? ORDER BY nombre_ruta";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + origen + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ruta ruta = new Ruta();
                    ruta.setIdRuta(rs.getInt("idRuta"));
                    ruta.setNombreRuta(rs.getString("nombre_ruta"));
                    ruta.setOrigen(rs.getString("origen"));
                    ruta.setDestino(rs.getString("destino"));
                    rutas.add(ruta);
                }
            }
        }
        return rutas;
    }

    /**
     * Busca rutas por destino (búsqueda parcial)
     * @param destino Destino o parte del destino a buscar
     * @return Lista de rutas que coinciden con la búsqueda
     * @throws SQLException Error en la consulta
     */
    public List<Ruta> findByDestino(String destino) throws SQLException {
        List<Ruta> rutas = new ArrayList<>();
        String query = "SELECT idRuta, nombre_ruta, origen, destino FROM Ruta WHERE destino LIKE ? ORDER BY nombre_ruta";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + destino + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ruta ruta = new Ruta();
                    ruta.setIdRuta(rs.getInt("idRuta"));
                    ruta.setNombreRuta(rs.getString("nombre_ruta"));
                    ruta.setOrigen(rs.getString("origen"));
                    ruta.setDestino(rs.getString("destino"));
                    rutas.add(ruta);
                }
            }
        }
        return rutas;
    }

    /**
     * Obtiene el conteo total de rutas
     * @return Número total de rutas
     * @throws SQLException Error en la consulta
     */
    public int count() throws SQLException {
        String query = "SELECT COUNT(*) FROM Ruta";

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
     * Verifica si existe una ruta con el mismo nombre
     * @param nombreRuta Nombre de la ruta a verificar
     * @return true si ya existe una ruta con ese nombre
     * @throws SQLException Error en la consulta
     */
    public boolean existsByNombre(String nombreRuta) throws SQLException {
        String query = "SELECT 1 FROM Ruta WHERE nombre_ruta = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreRuta);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Verifica si existe una ruta con el mismo nombre excluyendo un ID específico
     * @param nombreRuta Nombre de la ruta a verificar
     * @param idRutaExcluir ID de la ruta a excluir de la verificación
     * @return true si ya existe otra ruta con ese nombre
     * @throws SQLException Error en la consulta
     */
    public boolean existsByNombreExcludingId(String nombreRuta, int idRutaExcluir) throws SQLException {
        String query = "SELECT 1 FROM Ruta WHERE nombre_ruta = ? AND idRuta != ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreRuta);
            stmt.setInt(2, idRutaExcluir);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}