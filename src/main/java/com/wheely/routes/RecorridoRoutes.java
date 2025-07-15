package com.wheely.routes;

import io.javalin.Javalin;
import com.wheely.controller.RecorridoController;

public class RecorridoRoutes {
    private final RecorridoController recorridoController;

    public RecorridoRoutes(RecorridoController recorridoController) {
        this.recorridoController = recorridoController;
    }

    public void register(Javalin app) {
        app.get("/recorridos", recorridoController::getAll);
        app.get("/recorridos/{id}", recorridoController::getById);
        app.post("/recorridos", recorridoController::create);
        app.put("/recorridos/{id}", recorridoController::update);
        app.delete("/recorridos/{id}", recorridoController::delete);
    }
}