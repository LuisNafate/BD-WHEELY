package com.wheely.repository;

import com.wheely.config.DatabaseConfig;
import com.wheely.model.CoordenadaParada;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla CoordenadaParada
 * Maneja todas las interacciones con la base de datos para coordenadas de paradas
 */
public class CoordenadaParadaRepository {

    /**
     * Obtiene todas las coordenadas de parada de la base de datos
     * @return Lista de todas las coordenadas de parada
     * @throws SQLException Error en la consulta
     */
    public List<CoordenadaParada> findAll() throws SQLException {
        List<CoordenadaParada> coordenadasParada = new ArrayList<>();
        String query = "SELECT idCoordenadaParada, idParada, latitud, longitud, orden_parada FROM CoordenadaParada ORDER BY idParada, orden_parada";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CoordenadaParada coordenadaParada = new CoordenadaParada();
                coordenadaParada.setIdCoordenadaParada(rs.getInt("idCoordenadaParada"));
                coordenadaParada.setIdParada(rs.getInt("idParada"));
                coordenadaParada.setLatitud(rs.getBigDecimal("latitud"));
                coordenadaParada.setLongitud(rs.getBigDecimal("longitud"));
                coordenadaParada.setOrdenParada(rs.getInt("orden_parada"));
                coordenadasParada.add(coordenadaParada);
            }
        }
        return coordenadasParada;
    }

    /**
     * Busca una coordenada de parada por su ID
     * @param idCoordenadaParada ID de la coordenada de parada a buscar
     * @return Coordenada de parada encontrada o null si no existe
     * @throws SQLException Error en la consulta
     */
    public CoordenadaParada findById(int idCoordenadaParada) throws SQLException {
        CoordenadaParada coordenadaParada = null;
        String query = "SELECT idCoordenadaParada, idParada, latitud, longitud, orden_parada FROM CoordenadaParada WHERE idCoordenadaParada = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idCoordenadaParada);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    coordenadaParada = new CoordenadaParada();
                    coordenadaParada.setIdCoordenadaParada(rs.getInt("idCoordenadaParada"));
                    coordenadaParada.setIdParada(rs.getInt("idParada"));
                    coordenadaParada.setLatitud(rs.getBigDecimal("latitud"));
                    coordenadaParada.setLongitud(rs.getBigDecimal("longitud"));
                    coordenadaParada.setOrdenParada(rs.getInt("orden_parada"));
                }
            }
        }
        return coordenadaParada;
    }

    /**
     * Obtiene todas las coordenadas de una parada específica ordenadas por orden_parada
     * @param idParada ID de la parada
     * @return Lista de coordenadas de la parada ordenadas
     * @throws SQLException Error en la consulta
     */
    public List<CoordenadaParada> findByParada(int idParada) throws SQLException {
        List<CoordenadaParada> coordenadasParada = new ArrayList<>();
        String query = "SELECT idCoordenadaParada, idParada, latitud, longitud, orden_parada FROM CoordenadaParada WHERE idParada = ? ORDER BY orden_parada";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idParada);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CoordenadaParada coordenadaParada = new CoordenadaParada();
                    coordenadaParada.setIdCoordenadaParada(rs.getInt("idCoordenadaParada"));
                    coordenadaParada.setIdParada(rs.getInt("idParada"));
                    coordenadaParada.setLatitud(rs.getBigDecimal("latitud"));
                    coordenadaParada.setLongitud(rs.getBigDecimal("longitud"));
                    coordenadaParada.setOrdenParada(rs.getInt("orden_parada"));
                    coordenadasParada.add(coordenadaParada);
                }
            }
        }
        return coordenadasParada;
    }

    /**
     * Obtiene coordenadas de parada dentro de un rango geográfico (bounding box)
     * @param latitudMin Latitud mínima
     * @param latitudMax Latitud máxima
     * @param longitudMin Longitud mínima
     * @param longitudMax Longitud máxima
     * @return Lista de coordenadas de parada dentro del rango
     * @throws SQLException Error en la consulta
     */
    public List<CoordenadaParada> findByRangoGeografico(BigDecimal latitudMin, BigDecimal latitudMax,
                                                        BigDecimal longitudMin, BigDecimal longitudMax) throws SQLException {
        List<CoordenadaParada> coordenadasParada = new ArrayList<>();
        String query = "SELECT idCoordenadaParada, idParada, latitud, longitud, orden_parada FROM CoordenadaParada " +
                "WHERE latitud BETWEEN ? AND ? AND longitud BETWEEN ? AND ? " +
                "ORDER BY idParada, orden_parada";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBigDecimal(1, latitudMin);
            stmt.setBigDecimal(2, latitudMax);
            stmt.setBigDecimal(3, longitudMin);
            stmt.setBigDecimal(4, longitudMax);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CoordenadaParada coordenadaParada = new CoordenadaParada();
                    coordenadaParada.setIdCoordenadaParada(rs.getInt("idCoordenadaParada"));
                    coordenadaParada.setIdParada(rs.getInt("idParada"));
                    coordenadaParada.setLatitud(rs.getBigDecimal("latitud"));
                    coordenadaParada.setLongitud(rs.getBigDecimal("longitud"));
                    coordenadaParada.setOrdenParada(rs.getInt("orden_parada"));
                    coordenadasParada.add(coordenadaParada);
                }
            }
        }
        return coordenadasParada;
    }

    /**
     * Guarda una nueva coordenada de parada en la base de datos
     * @param coordenadaParada Coordenada de parada a guardar
     * @return ID de la coordenada de parada creada
     * @throws SQLException Error en la inserción
     */
    public int save(CoordenadaParada coordenadaParada) throws SQLException {
        String query = "INSERT INTO CoordenadaParada (idParada, latitud, longitud, orden_parada) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, coordenadaParada.getIdParada());
            stmt.setBigDecimal(2, coordenadaParada.getLatitud());
            stmt.setBigDecimal(3, coordenadaParada.getLongitud());
            stmt.setInt(4, coordenadaParada.getOrdenParada());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear coordenada de parada, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear coordenada de parada, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Guarda múltiples coordenadas de parada en batch para mejor rendimiento
     * @param coordenadasParada Lista de coordenadas de parada a guardar
     * @return Lista de IDs generados
     * @throws SQLException Error en la inserción
     */
    public List<Integer> saveBatch(List<CoordenadaParada> coordenadasParada) throws SQLException {
        List<Integer> idsGenerados = new ArrayList<>();
        String query = "INSERT INTO CoordenadaParada (idParada, latitud, longitud, orden_parada) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                for (CoordenadaParada coordenadaParada : coordenadasParada) {
                    stmt.setInt(1, coordenadaParada.getIdParada());
                    stmt.setBigDecimal(2, coordenadaParada.getLatitud());
                    stmt.setBigDecimal(3, coordenadaParada.getLongitud());
                    stmt.setInt(4, coordenadaParada.getOrdenParada());
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
     * Actualiza una coordenada de parada existente
     * @param coordenadaParada Coordenada de parada con los datos actualizados
     * @return true si se actualizó correctamente, false si no se encontró la coordenada de parada
     * @throws SQLException Error en la actualización
     */
    public boolean update(CoordenadaParada coordenadaParada) throws SQLException {
        String query = "UPDATE CoordenadaParada SET idParada = ?, latitud = ?, longitud = ?, orden_parada = ? WHERE idCoordenadaParada = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, coordenadaParada.getIdParada());
            stmt.setBigDecimal(2, coordenadaParada.getLatitud());
            stmt.setBigDecimal(3, coordenadaParada.getLongitud());
            stmt.setInt(4, coordenadaParada.getOrdenParada());
            stmt.setInt(5, coordenadaParada.getIdCoordenadaParada());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina una coordenada de parada por su ID
     * @param idCoordenadaParada ID de la coordenada de parada a eliminar
     * @return true si se eliminó correctamente, false si no se encontró la coordenada de parada
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idCoordenadaParada) throws SQLException {
        String query = "DELETE FROM CoordenadaParada WHERE idCoordenadaParada = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idCoordenadaParada);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina todas las coordenadas de una parada
     * @param idParada ID de la parada
     * @return Número de coordenadas eliminadas
     * @throws SQLException Error en la eliminación
     */
    public int deleteByParada(int idParada) throws SQLException {
        String query = "DELETE FROM CoordenadaParada WHERE idParada = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idParada);

            return stmt.executeUpdate();
        }
    }

    /**
     * Obtiene el siguiente número de orden para una parada
     * @param idParada ID de la parada
     * @return Siguiente número de orden disponible
     * @throws SQLException Error en la consulta
     */
    public int getNextOrdenParada(int idParada) throws SQLException {
        String query = "SELECT COALESCE(MAX(orden_parada), 0) + 1 FROM CoordenadaParada WHERE idParada = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idParada);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 1; // Si no hay coordenadas, empezar en 1
            }
        }
    }

    /**
     * Reordena las coordenadas de una parada
     * @param idParada ID de la parada
     * @throws SQLException Error en la actualización
     */
    public void reordenarCoordenadasParada(int idParada) throws SQLException {
        String selectQuery = "SELECT idCoordenadaParada FROM CoordenadaParada WHERE idParada = ? ORDER BY orden_parada";
        String updateQuery = "UPDATE CoordenadaParada SET orden_parada = ? WHERE idCoordenadaParada = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection()) {
            conn.setAutoCommit(false);

            try {
                // Obtener todas las coordenadas de la parada ordenadas
                List<Integer> coordenadasIds = new ArrayList<>();
                try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                    selectStmt.setInt(1, idParada);
                    try (ResultSet rs = selectStmt.executeQuery()) {
                        while (rs.next()) {
                            coordenadasIds.add(rs.getInt("idCoordenadaParada"));
                        }
                    }
                }

                // Actualizar el orden secuencialmente
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    for (int i = 0; i < coordenadasIds.size(); i++) {
                        updateStmt.setInt(1, i + 1); // Nuevo orden (empezando en 1)
                        updateStmt.setInt(2, coordenadasIds.get(i)); // ID de la coordenada
                        updateStmt.addBatch();
                    }
                    updateStmt.executeBatch();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    /**
     * Obtiene el conteo total de coordenadas de parada
     * @return Número total de coordenadas de parada
     * @throws SQLException Error en la consulta
     */
    public int count() throws SQLException {
        String query = "SELECT COUNT(*) FROM CoordenadaParada";

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
     * Obtiene el conteo de coordenadas por parada
     * @param idParada ID de la parada
     * @return Número de coordenadas de la parada
     * @throws SQLException Error en la consulta
     */
    public int countByParada(int idParada) throws SQLException {
        String query = "SELECT COUNT(*) FROM CoordenadaParada WHERE idParada = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idParada);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }
    }

    /**
     * Verifica si existe una coordenada de parada duplicada en la misma parada
     * @param idParada ID de la parada
     * @param latitud Latitud a verificar
     * @param longitud Longitud a verificar
     * @return true si ya existe una coordenada igual en la parada
     * @throws SQLException Error en la consulta
     */
    public boolean existsCoordenadaDuplicada(int idParada, BigDecimal latitud, BigDecimal longitud) throws SQLException {
        String query = "SELECT 1 FROM CoordenadaParada WHERE idParada = ? AND latitud = ? AND longitud = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idParada);
            stmt.setBigDecimal(2, latitud);
            stmt.setBigDecimal(3, longitud);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Busca coordenadas de parada cercanas a una ubicación específica
     * @param latitud Latitud de referencia
     * @param longitud Longitud de referencia
     * @param radioKm Radio de búsqueda en kilómetros
     * @return Lista de coordenadas de parada dentro del radio
     * @throws SQLException Error en la consulta
     */
    public List<CoordenadaParada> findCercanas(BigDecimal latitud, BigDecimal longitud, double radioKm) throws SQLException {
        List<CoordenadaParada> coordenadasParada = new ArrayList<>();

        // Usar fórmula de Haversine para calcular distancia
        String query = "SELECT idCoordenadaParada, idParada, latitud, longitud, orden_parada, " +
                "(6371 * acos(cos(radians(?)) * cos(radians(latitud)) * " +
                "cos(radians(longitud) - radians(?)) + sin(radians(?)) * sin(radians(latitud)))) AS distancia " +
                "FROM CoordenadaParada " +
                "HAVING distancia < ? " +
                "ORDER BY distancia";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBigDecimal(1, latitud);
            stmt.setBigDecimal(2, longitud);
            stmt.setBigDecimal(3, latitud);
            stmt.setDouble(4, radioKm);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CoordenadaParada coordenadaParada = new CoordenadaParada();
                    coordenadaParada.setIdCoordenadaParada(rs.getInt("idCoordenadaParada"));
                    coordenadaParada.setIdParada(rs.getInt("idParada"));
                    coordenadaParada.setLatitud(rs.getBigDecimal("latitud"));
                    coordenadaParada.setLongitud(rs.getBigDecimal("longitud"));
                    coordenadaParada.setOrdenParada(rs.getInt("orden_parada"));
                    coordenadasParada.add(coordenadaParada);
                }
            }
        }
        return coordenadasParada;
    }

    /**
     * Obtiene estadísticas de distribución geográfica de las paradas
     * @return Array con [latitud_min, latitud_max, longitud_min, longitud_max]
     * @throws SQLException Error en la consulta
     */
    public BigDecimal[] getEstadisticasGeograficas() throws SQLException {
        String query = "SELECT MIN(latitud) as lat_min, MAX(latitud) as lat_max, " +
                "MIN(longitud) as lng_min, MAX(longitud) as lng_max " +
                "FROM CoordenadaParada WHERE latitud IS NOT NULL AND longitud IS NOT NULL";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return new BigDecimal[]{
                        rs.getBigDecimal("lat_min"),
                        rs.getBigDecimal("lat_max"),
                        rs.getBigDecimal("lng_min"),
                        rs.getBigDecimal("lng_max")
                };
            }
            return new BigDecimal[4]; // Array vacío si no hay datos
        }
    }
}