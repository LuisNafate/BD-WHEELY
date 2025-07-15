package com.wheely.repository;

import com.wheely.config.DatabaseConfig;
import com.wheely.model.Coordenada;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla Coordenada
 * Maneja todas las interacciones con la base de datos para coordenadas de recorridos
 */
public class CoordenadaRepository {

    /**
     * Obtiene todas las coordenadas de la base de datos
     * @return Lista de todas las coordenadas
     * @throws SQLException Error en la consulta
     */
    public List<Coordenada> findAll() throws SQLException {
        List<Coordenada> coordenadas = new ArrayList<>();
        String query = "SELECT idCoordenada, idRecorrido, latitud, longitud, orden_punto FROM Coordenada ORDER BY idRecorrido, orden_punto";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Coordenada coordenada = new Coordenada();
                coordenada.setIdCoordenada(rs.getInt("idCoordenada"));
                coordenada.setIdRecorrido(rs.getInt("idRecorrido"));
                coordenada.setLatitud(rs.getBigDecimal("latitud"));
                coordenada.setLongitud(rs.getBigDecimal("longitud"));
                coordenada.setOrdenPunto(rs.getInt("orden_punto"));
                coordenadas.add(coordenada);
            }
        }
        return coordenadas;
    }

    /**
     * Busca una coordenada por su ID
     * @param idCoordenada ID de la coordenada a buscar
     * @return Coordenada encontrada o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Coordenada findById(int idCoordenada) throws SQLException {
        Coordenada coordenada = null;
        String query = "SELECT idCoordenada, idRecorrido, latitud, longitud, orden_punto FROM Coordenada WHERE idCoordenada = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idCoordenada);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    coordenada = new Coordenada();
                    coordenada.setIdCoordenada(rs.getInt("idCoordenada"));
                    coordenada.setIdRecorrido(rs.getInt("idRecorrido"));
                    coordenada.setLatitud(rs.getBigDecimal("latitud"));
                    coordenada.setLongitud(rs.getBigDecimal("longitud"));
                    coordenada.setOrdenPunto(rs.getInt("orden_punto"));
                }
            }
        }
        return coordenada;
    }

    /**
     * Obtiene todas las coordenadas de un recorrido específico ordenadas por orden_punto
     * @param idRecorrido ID del recorrido
     * @return Lista de coordenadas del recorrido ordenadas
     * @throws SQLException Error en la consulta
     */
    public List<Coordenada> findByRecorrido(int idRecorrido) throws SQLException {
        List<Coordenada> coordenadas = new ArrayList<>();
        String query = "SELECT idCoordenada, idRecorrido, latitud, longitud, orden_punto FROM Coordenada WHERE idRecorrido = ? ORDER BY orden_punto";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRecorrido);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Coordenada coordenada = new Coordenada();
                    coordenada.setIdCoordenada(rs.getInt("idCoordenada"));
                    coordenada.setIdRecorrido(rs.getInt("idRecorrido"));
                    coordenada.setLatitud(rs.getBigDecimal("latitud"));
                    coordenada.setLongitud(rs.getBigDecimal("longitud"));
                    coordenada.setOrdenPunto(rs.getInt("orden_punto"));
                    coordenadas.add(coordenada);
                }
            }
        }
        return coordenadas;
    }

    /**
     * Obtiene coordenadas dentro de un rango geográfico (bounding box)
     * @param latitudMin Latitud mínima
     * @param latitudMax Latitud máxima
     * @param longitudMin Longitud mínima
     * @param longitudMax Longitud máxima
     * @return Lista de coordenadas dentro del rango
     * @throws SQLException Error en la consulta
     */
    public List<Coordenada> findByRangoGeografico(BigDecimal latitudMin, BigDecimal latitudMax,
                                                  BigDecimal longitudMin, BigDecimal longitudMax) throws SQLException {
        List<Coordenada> coordenadas = new ArrayList<>();
        String query = "SELECT idCoordenada, idRecorrido, latitud, longitud, orden_punto FROM Coordenada " +
                "WHERE latitud BETWEEN ? AND ? AND longitud BETWEEN ? AND ? " +
                "ORDER BY idRecorrido, orden_punto";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBigDecimal(1, latitudMin);
            stmt.setBigDecimal(2, latitudMax);
            stmt.setBigDecimal(3, longitudMin);
            stmt.setBigDecimal(4, longitudMax);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Coordenada coordenada = new Coordenada();
                    coordenada.setIdCoordenada(rs.getInt("idCoordenada"));
                    coordenada.setIdRecorrido(rs.getInt("idRecorrido"));
                    coordenada.setLatitud(rs.getBigDecimal("latitud"));
                    coordenada.setLongitud(rs.getBigDecimal("longitud"));
                    coordenada.setOrdenPunto(rs.getInt("orden_punto"));
                    coordenadas.add(coordenada);
                }
            }
        }
        return coordenadas;
    }

    /**
     * Guarda una nueva coordenada en la base de datos
     * @param coordenada Coordenada a guardar
     * @return ID de la coordenada creada
     * @throws SQLException Error en la inserción
     */
    public int save(Coordenada coordenada) throws SQLException {
        String query = "INSERT INTO Coordenada (idRecorrido, latitud, longitud, orden_punto) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, coordenada.getIdRecorrido());
            stmt.setBigDecimal(2, coordenada.getLatitud());
            stmt.setBigDecimal(3, coordenada.getLongitud());
            stmt.setInt(4, coordenada.getOrdenPunto());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear coordenada, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear coordenada, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Guarda múltiples coordenadas en batch para mejor rendimiento
     * @param coordenadas Lista de coordenadas a guardar
     * @return Lista de IDs generados
     * @throws SQLException Error en la inserción
     */
    public List<Integer> saveBatch(List<Coordenada> coordenadas) throws SQLException {
        List<Integer> idsGenerados = new ArrayList<>();
        String query = "INSERT INTO Coordenada (idRecorrido, latitud, longitud, orden_punto) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                for (Coordenada coordenada : coordenadas) {
                    stmt.setInt(1, coordenada.getIdRecorrido());
                    stmt.setBigDecimal(2, coordenada.getLatitud());
                    stmt.setBigDecimal(3, coordenada.getLongitud());
                    stmt.setInt(4, coordenada.getOrdenPunto());
                    stmt.addBatch();
                }

                stmt.executeBatch();
                conn.commit();

                // Obtener IDs generados
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    while (generatedKeys.next()) {
                        idsGenerados.add(generatedKeys.getInt(1));
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
        return idsGenerados;
    }

    /**
     * Actualiza una coordenada existente
     * @param coordenada Coordenada con los datos actualizados
     * @return true si se actualizó correctamente, false si no se encontró la coordenada
     * @throws SQLException Error en la actualización
     */
    public boolean update(Coordenada coordenada) throws SQLException {
        String query = "UPDATE Coordenada SET idRecorrido = ?, latitud = ?, longitud = ?, orden_punto = ? WHERE idCoordenada = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, coordenada.getIdRecorrido());
            stmt.setBigDecimal(2, coordenada.getLatitud());
            stmt.setBigDecimal(3, coordenada.getLongitud());
            stmt.setInt(4, coordenada.getOrdenPunto());
            stmt.setInt(5, coordenada.getIdCoordenada());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina una coordenada por su ID
     * @param idCoordenada ID de la coordenada a eliminar
     * @return true si se eliminó correctamente, false si no se encontró la coordenada
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idCoordenada) throws SQLException {
        String query = "DELETE FROM Coordenada WHERE idCoordenada = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idCoordenada);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina todas las coordenadas de un recorrido
     * @param idRecorrido ID del recorrido
     * @return Número de coordenadas eliminadas
     * @throws SQLException Error en la eliminación
     */
    public int deleteByRecorrido(int idRecorrido) throws SQLException {
        String query = "DELETE FROM Coordenada WHERE idRecorrido = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRecorrido);

            return stmt.executeUpdate();
        }
    }

    /**
     * Obtiene el siguiente número de orden para un recorrido
     * @param idRecorrido ID del recorrido
     * @return Siguiente número de orden disponible
     * @throws SQLException Error en la consulta
     */
    public int getNextOrdenPunto(int idRecorrido) throws SQLException {
        String query = "SELECT COALESCE(MAX(orden_punto), 0) + 1 FROM Coordenada WHERE idRecorrido = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRecorrido);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 1; // Si no hay coordenadas, empezar en 1
            }
        }
    }

    /**
     * Reordena las coordenadas de un recorrido
     * @param idRecorrido ID del recorrido
     * @throws SQLException Error en la actualización
     */
    public void reordenarCoordenadas(int idRecorrido) throws SQLException {
        String query = "SET @row_number = 0; " +
                "UPDATE Coordenada SET orden_punto = (@row_number:=@row_number + 1) " +
                "WHERE idRecorrido = ? ORDER BY orden_punto";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRecorrido);
            stmt.executeUpdate();
        }
    }

    /**
     * Obtiene el conteo total de coordenadas
     * @return Número total de coordenadas
     * @throws SQLException Error en la consulta
     */
    public int count() throws SQLException {
        String query = "SELECT COUNT(*) FROM Coordenada";

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
     * Obtiene el conteo de coordenadas por recorrido
     * @param idRecorrido ID del recorrido
     * @return Número de coordenadas del recorrido
     * @throws SQLException Error en la consulta
     */
    public int countByRecorrido(int idRecorrido) throws SQLException {
        String query = "SELECT COUNT(*) FROM Coordenada WHERE idRecorrido = ?";

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
     * Verifica si existe una coordenada duplicada en el mismo recorrido
     * @param idRecorrido ID del recorrido
     * @param latitud Latitud a verificar
     * @param longitud Longitud a verificar
     * @return true si ya existe una coordenada igual en el recorrido
     * @throws SQLException Error en la consulta
     */
    public boolean existsCoordenadaDuplicada(int idRecorrido, BigDecimal latitud, BigDecimal longitud) throws SQLException {
        String query = "SELECT 1 FROM Coordenada WHERE idRecorrido = ? AND latitud = ? AND longitud = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRecorrido);
            stmt.setBigDecimal(2, latitud);
            stmt.setBigDecimal(3, longitud);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}