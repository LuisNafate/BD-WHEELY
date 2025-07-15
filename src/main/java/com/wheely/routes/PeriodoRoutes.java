package com.wheely.routes;

import io.javalin.Javalin;
import com.wheely.controller.PeriodoController;

/**
 * Configuración de rutas para la entidad Periodo
 * Define todos los endpoints REST para la gestión de periodos
 */
public class PeriodoRoutes {
    private final PeriodoController periodoController;

    public PeriodoRoutes(PeriodoController periodoController) {
        this.periodoController = periodoController;
    }

    /**
     * Registra todas las rutas de periodo en la aplicación Javalin
     * @param app Instancia de Javalin
     */
    public void register(Javalin app) {
        // Rutas principales de CRUD
        app.get("/api/periodos", periodoController::getAll);
        app.get("/api/periodos/{id}", periodoController::getById);
        app.post("/api/periodos", periodoController::create);
        app.put("/api/periodos/{id}", periodoController::update);
        app.delete("/api/periodos/{id}", periodoController::delete);

        // Rutas adicionales específicas
        app.get("/api/periodos/nombre/{nombre}", periodoController::getByNombre);
        app.get("/api/periodos/actual", periodoController::getActual);
    }
}