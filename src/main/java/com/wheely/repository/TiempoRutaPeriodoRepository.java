package com.wheely.repository;

import com.wheely.config.DatabaseConfig;
import com.wheely.model.TiempoRutaPeriodo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones de base de datos de la entidad TiempoRutaPeriodo
 * Maneja todas las consultas relacionadas con los tiempos promedio de rutas por periodo
 */
public class TiempoRutaPeriodoRepository {

    /**
     * Obtiene todos los tiempos de ruta por periodo
     * @return Lista de todos los tiempos ruta-periodo
     * @throws SQLException Si hay error en la consulta
     */
    public List<TiempoRutaPeriodo> findAll() throws SQLException {
        List<TiempoRutaPeriodo> tiempos = new ArrayList<>();
        String sql = "SELECT idTiempoRutaPeriodo, idRuta, idPeriodo, tiempo_promedio FROM TiempoRutaPeriodo ORDER BY idRuta, idPeriodo";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tiempos.add(mapResultSetToTiempoRutaPeriodo(rs));
            }
        }
        return tiempos;
    }

    /**
     * Busca un tiempo ruta-periodo por su ID
     * @param id ID del tiempo a buscar
     * @return TiempoRutaPeriodo encontrado o null si no existe
     * @throws SQLException Si hay error en la consulta
     */
    public TiempoRutaPeriodo findById(int id) throws SQLException {
        String sql = "SELECT idTiempoRutaPeriodo, idRuta, idPeriodo, tiempo_promedio FROM TiempoRutaPeriodo WHERE idTiempoRutaPeriodo = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTiempoRutaPeriodo(rs);
                }
            }
        }
        return null;
    }

    /**
     * Busca tiempos por ruta específica
     * @param idRuta ID de la ruta
     * @return Lista de tiempos para la ruta especificada
     * @throws SQLException Si hay error en la consulta
     */
    public List<TiempoRutaPeriodo> findByRuta(int idRuta) throws SQLException {
        List<TiempoRutaPeriodo> tiempos = new ArrayList<>();
        String sql = "SELECT idTiempoRutaPeriodo, idRuta, idPeriodo, tiempo_promedio FROM TiempoRutaPeriodo WHERE idRuta = ? ORDER BY idPeriodo";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRuta);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tiempos.add(mapResultSetToTiempoRutaPeriodo(rs));
                }
            }
        }
        return tiempos;
    }

    /**
     * Busca tiempos por periodo específico
     * @param idPeriodo ID del periodo
     * @return Lista de tiempos para el periodo especificado
     * @throws SQLException Si hay error en la consulta
     */
    public List<TiempoRutaPeriodo> findByPeriodo(int idPeriodo) throws SQLException {
        List<TiempoRutaPeriodo> tiempos = new ArrayList<>();
        String sql = "SELECT idTiempoRutaPeriodo, idRuta, idPeriodo, tiempo_promedio FROM TiempoRutaPeriodo WHERE idPeriodo = ? ORDER BY idRuta";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPeriodo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tiempos.add(mapResultSetToTiempoRutaPeriodo(rs));
                }
            }
        }
        return tiempos;
    }

    /**
     * Busca el tiempo específico para una combinación ruta-periodo
     * @param idRuta ID de la ruta
     * @param idPeriodo ID del periodo
     * @return TiempoRutaPeriodo encontrado o null si no existe
     * @throws SQLException Si hay error en la consulta
     */
    public TiempoRutaPeriodo findByRutaAndPeriodo(int idRuta, int idPeriodo) throws SQLException {
        String sql = "SELECT idTiempoRutaPeriodo, idRuta, idPeriodo, tiempo_promedio FROM TiempoRutaPeriodo WHERE idRuta = ? AND idPeriodo = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRuta);
            stmt.setInt(2, idPeriodo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTiempoRutaPeriodo(rs);
                }
            }
        }
        return null;
    }

    /**
     * Guarda un nuevo tiempo ruta-periodo
     * @param tiempoRutaPeriodo Tiempo a guardar
     * @return ID del tiempo creado
     * @throws SQLException Si hay error en la consulta
     */
    public int save(TiempoRutaPeriodo tiempoRutaPeriodo) throws SQLException {
        String sql = "INSERT INTO TiempoRutaPeriodo (idRuta, idPeriodo, tiempo_promedio) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, tiempoRutaPeriodo.getIdRuta());
            stmt.setInt(2, tiempoRutaPeriodo.getIdPeriodo());
            stmt.setInt(3, tiempoRutaPeriodo.getTiempoPromedio());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        throw new SQLException("Error al crear tiempo ruta-periodo, no se obtuvo ID");
    }

    /**
     * Actualiza un tiempo ruta-periodo existente
     * @param tiempoRutaPeriodo Tiempo con los datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException Si hay error en la consulta
     */
    public boolean update(TiempoRutaPeriodo tiempoRutaPeriodo) throws SQLException {
        String sql = "UPDATE TiempoRutaPeriodo SET idRuta = ?, idPeriodo = ?, tiempo_promedio = ? WHERE idTiempoRutaPeriodo = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tiempoRutaPeriodo.getIdRuta());
            stmt.setInt(2, tiempoRutaPeriodo.getIdPeriodo());
            stmt.setInt(3, tiempoRutaPeriodo.getTiempoPromedio());
            stmt.setInt(4, tiempoRutaPeriodo.getIdTiempoRutaPeriodo());

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un tiempo ruta-periodo
     * @param id ID del tiempo a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException Si hay error en la consulta
     */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM TiempoRutaPeriodo WHERE idTiempoRutaPeriodo = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Elimina todos los tiempos de una ruta específica
     * @param idRuta ID de la ruta
     * @return true si se eliminaron registros
     * @throws SQLException Si hay error en la consulta
     */
    public boolean deleteByRuta(int idRuta) throws SQLException {
        String sql = "DELETE FROM TiempoRutaPeriodo WHERE idRuta = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRuta);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Verifica si existe una combinación ruta-periodo
     * @param idRuta ID de la ruta
     * @param idPeriodo ID del periodo
     * @return true si existe la combinación
     * @throws SQLException Si hay error en la consulta
     */
    public boolean existsRutaPeriodo(int idRuta, int idPeriodo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM TiempoRutaPeriodo WHERE idRuta = ? AND idPeriodo = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRuta);
            stmt.setInt(2, idPeriodo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    /**
     * Mapea un ResultSet a un objeto TiempoRutaPeriodo
     * @param rs ResultSet con los datos del tiempo
     * @return Objeto TiempoRutaPeriodo mapeado
     * @throws SQLException Si hay error al acceder a los datos
     */
    private TiempoRutaPeriodo mapResultSetToTiempoRutaPeriodo(ResultSet rs) throws SQLException {
        return new TiempoRutaPeriodo(
                rs.getInt("idTiempoRutaPeriodo"),
                rs.getInt("idRuta"),
                rs.getInt("idPeriodo"),
                rs.getInt("tiempo_promedio")
        );
    }
}