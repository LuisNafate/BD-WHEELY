package com.wheely.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;

import javax.sql.DataSource;

/**
 * Configuración de la conexión a la base de datos MySQL
 * Usa HikariCP como pool de conexiones para mejor rendimiento
 */
public class DatabaseConfig {
    private static HikariDataSource dataSource;

    /**
     * Obtiene la fuente de datos configurada
     * Implementa patrón Singleton para reutilizar la conexión
     * @return DataSource configurado con las credenciales del archivo .env
     */
    public static DataSource getDataSource() {
        if (dataSource == null) {
            // Cargar variables de entorno desde archivo .env
            Dotenv dotenv = Dotenv.load();

            // Obtener configuración de base de datos
            String host = dotenv.get("DB_HOST");
            String dbName = dotenv.get("DB_SCHEMA");
            String jdbcUrl = String.format("jdbc:mysql://%s:3306/%s", host, dbName);

            // Configurar HikariCP (pool de conexiones)
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(dotenv.get("DB_USER"));
            config.setPassword(dotenv.get("DB_PASS"));
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            // Configuraciones adicionales para optimizar el pool
            config.setMaximumPoolSize(20);
            config.setMinimumIdle(5);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(1800000);

            // Configuraciones adicionales para MySQL
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.addDataSourceProperty("useServerPrepStmts", "true");
            config.addDataSourceProperty("useLocalSessionState", "true");
            config.addDataSourceProperty("rewriteBatchedStatements", "true");
            config.addDataSourceProperty("cacheResultSetMetadata", "true");
            config.addDataSourceProperty("cacheServerConfiguration", "true");
            config.addDataSourceProperty("elideSetAutoCommits", "true");
            config.addDataSourceProperty("maintainTimeStats", "false");

            dataSource = new HikariDataSource(config);
            System.out.println(" Conexión a base de datos configurada: " + dbName);
        }
        return dataSource;
    }

    /**
     * Cierra el pool de conexiones
     */
    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println(" Pool de conexiones cerrado");
        }
    }

    /**
     * Verifica si la conexión a la base de datos funciona
     * @return true si la conexión es exitosa
     */
    public static boolean testConnection() {
        try (var connection = getDataSource().getConnection()) {
            return connection.isValid(5);
        } catch (Exception e) {
            System.err.println(" Error al conectar con la base de datos: " + e.getMessage());
            return false;
        }
    }
}