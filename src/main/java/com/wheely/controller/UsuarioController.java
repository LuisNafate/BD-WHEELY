package com.wheely.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.wheely.model.Usuario;
import com.wheely.service.UsuarioService;
import com.wheely.util.ApiResponse;

import java.sql.SQLException;
import java.util.List;

public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET /api/usuarios
    public void getAll(Context ctx) {
        try {
            List<Usuario> usuarios = usuarioService.getAllUsuarios();
            ctx.json(ApiResponse.success("Usuarios obtenidos", usuarios));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener usuarios"));
        }
    }

    // GET /api/usuarios/{id}
    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Usuario usuario = usuarioService.getUsuarioById(id);
            if (usuario != null) {
                ctx.json(ApiResponse.success("Usuario encontrado", usuario));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Usuario"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID no válido"));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener usuario"));
        }
    }

    // POST /api/usuarios
    public void create(Context ctx) {
        try {
            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            int id = usuarioService.createUsuario(usuario);
            usuario.setIdUsuario(id);
            usuario.setPassword(""); // Limpiar password
            ctx.status(HttpStatus.CREATED)
                    .json(ApiResponse.success("Usuario creado", usuario));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al crear usuario"));
        }
    }

    // PUT /api/usuarios/{id}
    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            usuario.setIdUsuario(id);

            boolean updated = usuarioService.updateUsuario(usuario);
            if (updated) {
                usuario.setPassword(""); // Limpiar password
                ctx.json(ApiResponse.success("Usuario actualizado", usuario));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Usuario"));
            }
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al actualizar usuario"));
        }
    }

    // DELETE /api/usuarios/{id}
    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = usuarioService.deleteUsuario(id);
            if (deleted) {
                ctx.json(ApiResponse.success("Usuario eliminado"));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Usuario"));
            }
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al eliminar usuario"));
        }
    }

    // POST /usuarios/login - CORREGIDO PARA USAR VERIFICACIÓN DE CONTRASEÑAS ENCRIPTADAS
    public void login(Context ctx) {
        try {
            var loginData = ctx.bodyAsClass(LoginRequest.class);

            // Validar que se proporcionen email y password
            if (loginData.email == null || loginData.email.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(ApiResponse.error("Email es requerido"));
                return;
            }

            if (loginData.password == null || loginData.password.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(ApiResponse.error("Password es requerido"));
                return;
            }

            // Usar el método login del servicio que verifica contraseñas encriptadas
            Usuario usuario = usuarioService.login(loginData.email.trim(), loginData.password);

            if (usuario != null) {
                // Login exitoso - el password ya fue limpiado en el servicio
                ctx.json(ApiResponse.success("Login exitoso", usuario));
            } else {
                // Credenciales incorrectas
                ctx.status(HttpStatus.UNAUTHORIZED)
                        .json(ApiResponse.error("Email o contraseña incorrectos"));
            }

        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error interno del servidor"));
        } catch (Exception e) {
            // Log del error para debugging
            System.err.println("Error inesperado en login: " + e.getMessage());
            e.printStackTrace();
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error inesperado en el servidor"));
        }
    }

    // POST /usuarios/register - NUEVO MÉTODO ESPECÍFICO PARA REGISTRO
    public void register(Context ctx) {
        try {
            Usuario usuario = ctx.bodyAsClass(Usuario.class);

            // Validar datos básicos
            if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(ApiResponse.error("Nombre es requerido"));
                return;
            }

            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(ApiResponse.error("Email es requerido"));
                return;
            }

            if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(ApiResponse.error("Password es requerido"));
                return;
            }

            // Usar el método register del servicio que encripta la contraseña
            int id = usuarioService.register(usuario);
            usuario.setIdUsuario(id);
            usuario.setPassword(""); // Limpiar password antes de devolver

            ctx.status(HttpStatus.CREATED)
                    .json(ApiResponse.success("Usuario registrado exitosamente", usuario));

        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (SQLException e) {
            // Verificar si es error de email duplicado
            if (e.getMessage() != null && e.getMessage().contains("Duplicate entry")) {
                ctx.status(HttpStatus.CONFLICT)
                        .json(ApiResponse.error("El email ya está registrado"));
            } else {
                ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .json(ApiResponse.error("Error al registrar usuario"));
            }
        } catch (Exception e) {
            System.err.println("Error inesperado en registro: " + e.getMessage());
            e.printStackTrace();
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error inesperado en el servidor"));
        }
    }

    // Clase para request de login
    public static class LoginRequest {
        public String email;
        public String password;

        // Constructor vacío para Jackson
        public LoginRequest() {}

        // Constructor con parámetros
        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}