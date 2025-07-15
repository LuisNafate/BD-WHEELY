package com.wheely.routes;

import io.javalin.Javalin;
import com.wheely.controller.RutaController;

public class RutaRoutes {
    private final RutaController rutaController;

    public RutaRoutes(RutaController rutaController) {
        this.rutaController = rutaController;
    }

    public void register(Javalin app) {
        app.get("/rutas", rutaController::getAll);
        app.get("/rutas/{id}", rutaController::getById);
        app.post("/rutas", rutaController::create);
        app.put("/rutas/{id}", rutaController::update);
        app.delete("/rutas/{id}", rutaController::delete);
        app.get("/rutas/buscar", rutaController::buscarPorOrigenDestino);
    }
}