package com.wheely.service;

import com.wheely.model.Usuario;
import com.wheely.repository.UsuarioRepository;
import com.wheely.util.PasswordUtil;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // CRUD básico
    public List<Usuario> getAllUsuarios() throws SQLException {
        List<Usuario> usuarios = usuarioRepository.findAll();
        usuarios.forEach(u -> u.setPassword("")); // Limpiar passwords
        return usuarios;
    }

    public Usuario getUsuarioById(int id) throws SQLException {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario != null) usuario.setPassword("");
        return usuario;
    }

    public int createUsuario(Usuario usuario) throws SQLException {
        validateUsuario(usuario);
        if (usuarioRepository.emailExists(usuario.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (!PasswordUtil.isValidPassword(usuario.getPassword())) {
            throw new IllegalArgumentException("Contraseña no válida");
        }
        usuario.setPassword(PasswordUtil.hashPassword(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public boolean updateUsuario(Usuario usuario) throws SQLException {
        validateUsuario(usuario);
        if (usuarioRepository.emailExistsExcludingUser(usuario.getEmail(), usuario.getIdUsuario())) {
            throw new IllegalArgumentException("El email ya está en uso");
        }
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            if (!PasswordUtil.isValidPassword(usuario.getPassword())) {
                throw new IllegalArgumentException("Contraseña no válida");
            }
            usuario.setPassword(PasswordUtil.hashPassword(usuario.getPassword()));
        }
        return usuarioRepository.update(usuario);
    }

    public boolean deleteUsuario(int id) throws SQLException {
        return usuarioRepository.delete(id);
    }

    // Autenticación - MÉTODO LOGIN CORREGIDO
    public Usuario login(String email, String password) throws SQLException {
        // Validar parámetros
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email no puede estar vacío");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password no puede estar vacío");
        }

        // Buscar usuario por email
        Usuario usuario = usuarioRepository.findByEmail(email.trim());

        if (usuario != null) {
            // Verificar la contraseña encriptada
            if (PasswordUtil.verifyPassword(password, usuario.getPassword())) {
                // Contraseña correcta - limpiar password antes de devolver
                usuario.setPassword("");
                return usuario;
            }
        }

        // Email no encontrado o contraseña incorrecta
        return null;
    }

    // MÉTODO REGISTER AÑADIDO/CORREGIDO
    public int register(Usuario usuario) throws SQLException {
        // Validar datos del usuario
        validateUsuario(usuario);

        // Verificar que el email no exista
        if (usuarioRepository.emailExists(usuario.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        // Validar contraseña
        if (!PasswordUtil.isValidPassword(usuario.getPassword())) {
            String[] errors = PasswordUtil.getPasswordValidationErrors(usuario.getPassword());
            throw new IllegalArgumentException("Contraseña no válida: " + String.join(", ", errors));
        }

        // Encriptar contraseña
        usuario.setPassword(PasswordUtil.hashPassword(usuario.getPassword()));

        // Guardar usuario
        return usuarioRepository.save(usuario);
    }

    // Validación privada
    private void validateUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no puede ser nulo");
        }
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre es requerido");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email es requerido");
        }
        if (!usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Email no válido");
        }

        // Limpiar espacios
        usuario.setNombre(usuario.getNombre().trim());
        usuario.setEmail(usuario.getEmail().trim());
    }
}