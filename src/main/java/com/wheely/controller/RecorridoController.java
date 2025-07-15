package com.wheely.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.wheely.service.RecorridoService;
import com.wheely.util.ApiResponse;

public class RecorridoController {
    private final RecorridoService recorridoService;

    public RecorridoController(RecorridoService recorridoService) {
        this.recorridoService = recorridoService;
    }

    public void getAll(Context ctx) {
        try {
            ctx.json(ApiResponse.success("Recorridos obtenidos", recorridoService.getAllRecorridos()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var recorrido = recorridoService.getRecorridoById(id);
            ctx.json(recorrido != null ? ApiResponse.success("Recorrido encontrado", recorrido) : ApiResponse.notFound("Recorrido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void create(Context ctx) {
        try {
            var recorrido = ctx.bodyAsClass(com.wheely.model.Recorrido.class);
            int id = recorridoService.createRecorrido(recorrido);
            recorrido.setIdRecorrido(id);
            ctx.status(HttpStatus.CREATED).json(ApiResponse.success("Recorrido creado", recorrido));
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ApiResponse.error("Error"));
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var recorrido = ctx.bodyAsClass(com.wheely.model.Recorrido.class);
            recorrido.setIdRecorrido(id);
            ctx.json(recorridoService.updateRecorrido(recorrido) ?
                    ApiResponse.success("Recorrido actualizado", recorrido) : ApiResponse.notFound("Recorrido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(recorridoService.deleteRecorrido(id) ?
                    ApiResponse.success("Recorrido eliminado") : ApiResponse.notFound("Recorrido"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }
}