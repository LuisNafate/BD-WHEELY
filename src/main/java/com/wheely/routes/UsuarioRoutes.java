package com.wheely.routes;

import io.javalin.Javalin;
import com.wheely.controller.UsuarioController;

public class UsuarioRoutes {
    private final UsuarioController usuarioController;

    public UsuarioRoutes(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    public void register(Javalin app) {
        // Rutas CRUD básicas
        app.get("/usuarios", usuarioController::getAll);
        app.get("/usuarios/{id}", usuarioController::getById);
        app.post("/usuarios", usuarioController::create);
        app.put("/usuarios/{id}", usuarioController::update);
        app.delete("/usuarios/{id}", usuarioController::delete);

        // Rutas de autenticación
        app.post("/usuarios/login", usuarioController::login);
        app.post("/usuarios/register", usuarioController::register);
    }
}