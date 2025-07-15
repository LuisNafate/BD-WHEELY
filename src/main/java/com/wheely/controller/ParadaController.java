package com.wheely.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.wheely.service.ParadaService;
import com.wheely.util.ApiResponse;

public class ParadaController {
    private final ParadaService paradaService;

    public ParadaController(ParadaService paradaService) {
        this.paradaService = paradaService;
    }

    public void getAll(Context ctx) {
        try {
            ctx.json(ApiResponse.success("Paradas obtenidas", paradaService.getAllParadas()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var parada = paradaService.getParadaById(id);
            ctx.json(parada != null ? ApiResponse.success("Parada encontrada", parada) : ApiResponse.notFound("Parada"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void create(Context ctx) {
        try {
            var parada = ctx.bodyAsClass(com.wheely.model.Parada.class);
            var paradaCreada = paradaService.createParada(parada);
            ctx.status(HttpStatus.CREATED).json(ApiResponse.success("Parada creada", paradaCreada));
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ApiResponse.error("Error"));
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var parada = ctx.bodyAsClass(com.wheely.model.Parada.class);
            parada.setIdParada(id);
            ctx.json(paradaService.updateParada(parada) ?
                    ApiResponse.success("Parada actualizada", parada) : ApiResponse.notFound("Parada"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(paradaService.deleteParada(id) ?
                    ApiResponse.success("Parada eliminada") : ApiResponse.notFound("Parada"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }
}