package com.wheely.repository;

import com.wheely.config.DatabaseConfig;
import com.wheely.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de la tabla Usuario
 * Maneja todas las interacciones con la base de datos para usuarios
 */
public class UsuarioRepository {

    /**
     * Obtiene todos los usuarios de la base de datos
     * @return Lista de todos los usuarios
     * @throws SQLException Error en la consulta
     */
    public List<Usuario> findAll() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT idUsuario, nombre, email, password FROM Usuario ORDER BY nombre";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPassword(rs.getString("password"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    /**
     * Busca un usuario por su ID
     * @param idUsuario ID del usuario a buscar
     * @return Usuario encontrado o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Usuario findById(int idUsuario) throws SQLException {
        Usuario usuario = null;
        String query = "SELECT idUsuario, nombre, email, password FROM Usuario WHERE idUsuario = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("idUsuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setPassword(rs.getString("password"));
                }
            }
        }
        return usuario;
    }

    /**
     * Busca un usuario por su email
     * @param email Email del usuario a buscar
     * @return Usuario encontrado o null si no existe
     * @throws SQLException Error en la consulta
     */
    public Usuario findByEmail(String email) throws SQLException {
        Usuario usuario = null;
        String query = "SELECT idUsuario, nombre, email, password FROM Usuario WHERE email = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("idUsuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setPassword(rs.getString("password"));
                }
            }
        }
        return usuario;
    }

    /**
     * Guarda un nuevo usuario en la base de datos
     * @param usuario Usuario a guardar
     * @return ID del usuario creado
     * @throws SQLException Error en la inserción
     */
    public int save(Usuario usuario) throws SQLException {
        String query = "INSERT INTO Usuario (nombre, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getPassword());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear usuario, no se insertaron filas");
            }

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al crear usuario, no se obtuvo el ID");
                }
            }
        }
    }

    /**
     * Actualiza un usuario existente
     * @param usuario Usuario con los datos actualizados
     * @return true si se actualizó correctamente, false si no se encontró el usuario
     * @throws SQLException Error en la actualización
     */
    public boolean update(Usuario usuario) throws SQLException {
        String query = "UPDATE Usuario SET nombre = ?, email = ?, password = ? WHERE idUsuario = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getPassword());
            stmt.setInt(4, usuario.getIdUsuario());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Elimina un usuario por su ID
     * @param idUsuario ID del usuario a eliminar
     * @return true si se eliminó correctamente, false si no se encontró el usuario
     * @throws SQLException Error en la eliminación
     */
    public boolean delete(int idUsuario) throws SQLException {
        String query = "DELETE FROM Usuario WHERE idUsuario = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUsuario);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Verifica si un email ya existe en la base de datos
     * @param email Email a verificar
     * @return true si el email existe, false en caso contrario
     * @throws SQLException Error en la consulta
     */
    public boolean emailExists(String email) throws SQLException {
        String query = "SELECT 1 FROM Usuario WHERE email = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Verifica si un email ya existe en la base de datos, excluyendo un usuario específico
     * Útil para validaciones en actualizaciones
     * @param email Email a verificar
     * @param excludeUserId ID del usuario a excluir de la verificación
     * @return true si el email existe en otro usuario, false en caso contrario
     * @throws SQLException Error en la consulta
     */
    public boolean emailExistsExcludingUser(String email, int excludeUserId) throws SQLException {
        String query = "SELECT 1 FROM Usuario WHERE email = ? AND idUsuario != ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setInt(2, excludeUserId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Obtiene el conteo total de usuarios
     * @return Número total de usuarios
     * @throws SQLException Error en la consulta
     */
    public int count() throws SQLException {
        String query = "SELECT COUNT(*) FROM Usuario";

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
     * Busca usuarios por nombre (búsqueda parcial)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de usuarios que coinciden con la búsqueda
     * @throws SQLException Error en la consulta
     */
    public List<Usuario> findByNombre(String nombre) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT idUsuario, nombre, email, password FROM Usuario WHERE nombre LIKE ? ORDER BY nombre";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + nombre + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("idUsuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setPassword(rs.getString("password"));
                    usuarios.add(usuario);
                }
            }
        }
        return usuarios;
    }
}