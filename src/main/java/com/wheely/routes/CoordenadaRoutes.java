package com.wheely.routes;

import io.javalin.Javalin;
import com.wheely.controller.CoordenadaController;

public class CoordenadaRoutes {
    private final CoordenadaController coordenadaController;

    public CoordenadaRoutes(CoordenadaController coordenadaController) {
        this.coordenadaController = coordenadaController;
    }

    public void register(Javalin app) {
        app.get("/coordenadas", coordenadaController::getAll);
        app.get("/coordenadas/{id}", coordenadaController::getById);
        app.post("/coordenadas", coordenadaController::create);
        app.put("/coordenadas/{id}", coordenadaController::update);
        app.delete("/coordenadas/{id}", coordenadaController::delete);
    }
}