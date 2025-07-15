package com.wheely.routes;

import io.javalin.Javalin;
import com.wheely.controller.ReporteController;

public class ReporteRoutes {
    private final ReporteController reporteController;

    public ReporteRoutes(ReporteController reporteController) {
        this.reporteController = reporteController;
    }

    public void register(Javalin app) {
        app.get("/reportes", reporteController::getAll);
        app.get("/reportes/{id}", reporteController::getById);
        app.post("/reportes", reporteController::create);
        app.put("/reportes/{id}", reporteController::update);
        app.delete("/reportes/{id}", reporteController::delete);
    }
}