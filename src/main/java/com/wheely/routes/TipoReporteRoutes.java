package com.wheely.routes;

import io.javalin.Javalin;
import com.wheely.controller.TipoReporteController;

public class TipoReporteRoutes {
    private final TipoReporteController tipoReporteController;

    public TipoReporteRoutes(TipoReporteController tipoReporteController) {
        this.tipoReporteController = tipoReporteController;
    }

    public void register(Javalin app) {
        app.get("/tipos-reporte", tipoReporteController::getAll);
        app.get("/tipos-reporte/{id}", tipoReporteController::getById);
        app.post("/tipos-reporte", tipoReporteController::create);
        app.put("/tipos-reporte/{id}", tipoReporteController::update);
        app.delete("/tipos-reporte/{id}", tipoReporteController::delete);
    }
}