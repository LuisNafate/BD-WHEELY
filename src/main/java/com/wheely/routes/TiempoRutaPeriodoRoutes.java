package com.wheely.routes;

import io.javalin.Javalin;
import com.wheely.controller.TiempoRutaPeriodoController;

public class TiempoRutaPeriodoRoutes {
    private final TiempoRutaPeriodoController tiempoRutaPeriodoController;

    public TiempoRutaPeriodoRoutes(TiempoRutaPeriodoController tiempoRutaPeriodoController) {
        this.tiempoRutaPeriodoController = tiempoRutaPeriodoController;
    }

    public void register(Javalin app) {
        app.get("/api/tiempos-ruta-periodo", tiempoRutaPeriodoController::getAll);
        app.get("/api/tiempos-ruta-periodo/{id}", tiempoRutaPeriodoController::getById);
        app.post("/api/tiempos-ruta-periodo", tiempoRutaPeriodoController::create);
        app.put("/api/tiempos-ruta-periodo/{id}", tiempoRutaPeriodoController::update);
        app.delete("/api/tiempos-ruta-periodo/{id}", tiempoRutaPeriodoController::delete);
        app.get("/api/tiempos-ruta-periodo/ruta/{idRuta}", tiempoRutaPeriodoController::getByRuta);
        app.get("/api/tiempos-ruta-periodo/periodo/{idPeriodo}", tiempoRutaPeriodoController::getByPeriodo);
        app.get("/api/tiempos-ruta-periodo/ruta/{idRuta}/periodo/{idPeriodo}", tiempoRutaPeriodoController::getByRutaAndPeriodo);
    }
}