package com.wheely.routes;

import io.javalin.Javalin;
import com.wheely.controller.ParadaController;

public class ParadaRoutes {
    private final ParadaController paradaController;

    public ParadaRoutes(ParadaController paradaController) {
        this.paradaController = paradaController;
    }

    public void register(Javalin app) {
        app.get("/paradas", paradaController::getAll);
        app.get("/paradas/{id}", paradaController::getById);
        app.post("/paradas", paradaController::create);
        app.put("/paradas/{id}", paradaController::update);
        app.delete("/paradas/{id}", paradaController::delete);
    }
}