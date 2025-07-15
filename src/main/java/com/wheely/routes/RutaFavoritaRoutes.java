package com.wheely.routes;

import io.javalin.Javalin;
import com.wheely.controller.RutaFavoritaController;

public class RutaFavoritaRoutes {
    private final RutaFavoritaController rutaFavoritaController;

    public RutaFavoritaRoutes(RutaFavoritaController rutaFavoritaController) {
        this.rutaFavoritaController = rutaFavoritaController;
    }

    public void register(Javalin app) {
        app.get("/rutas-favoritas", rutaFavoritaController::getAll);
        app.get("/rutas-favoritas/{id}", rutaFavoritaController::getById);
        app.get("/usuarios/{usuarioId}/rutas-favoritas", rutaFavoritaController::getByUsuario);
        app.post("/usuarios/{usuarioId}/rutas-favoritas", rutaFavoritaController::agregarFavorita);
        app.delete("/usuarios/{usuarioId}/rutas-favoritas/{rutaId}", rutaFavoritaController::eliminarFavorita);
    }
}