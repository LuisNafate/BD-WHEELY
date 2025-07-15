package com.wheely.routes;

import io.javalin.Javalin;
import com.wheely.controller.CoordenadaParadaController;

public class CoordenadaParadaRoutes {
    private final CoordenadaParadaController coordenadaParadaController;

    public CoordenadaParadaRoutes(CoordenadaParadaController coordenadaParadaController) {
        this.coordenadaParadaController = coordenadaParadaController;
    }

    public void register(Javalin app) {
        app.get("/coordenadas-parada", coordenadaParadaController::getAll);
        app.get("/coordenadas-parada/{id}", coordenadaParadaController::getById);
        app.post("/coordenadas-parada", coordenadaParadaController::create);
        app.put("/coordenadas-parada/{id}", coordenadaParadaController::update);
        app.delete("/coordenadas-parada/{id}", coordenadaParadaController::delete);
    }
}