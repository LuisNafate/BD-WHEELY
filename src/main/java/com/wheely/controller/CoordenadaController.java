package com.wheely.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.wheely.service.CoordenadaService;
import com.wheely.util.ApiResponse;

public class CoordenadaController {
    private final CoordenadaService coordenadaService;

    public CoordenadaController(CoordenadaService coordenadaService) {
        this.coordenadaService = coordenadaService;
    }

    public void getAll(Context ctx) {
        try {
            ctx.json(ApiResponse.success("Coordenadas obtenidas", coordenadaService.getAllCoordenadas()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var coordenada = coordenadaService.getCoordenadaById(id);
            ctx.json(coordenada != null ? ApiResponse.success("Coordenada encontrada", coordenada) : ApiResponse.notFound("Coordenada"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void create(Context ctx) {
        try {
            var coordenada = ctx.bodyAsClass(com.wheely.model.Coordenada.class);
            int id = coordenadaService.createCoordenada(coordenada);
            coordenada.setIdCoordenada(id);
            ctx.status(HttpStatus.CREATED).json(ApiResponse.success("Coordenada creada", coordenada));
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ApiResponse.error("Error"));
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var coordenada = ctx.bodyAsClass(com.wheely.model.Coordenada.class);
            coordenada.setIdCoordenada(id);
            ctx.json(coordenadaService.updateCoordenada(coordenada) ?
                    ApiResponse.success("Coordenada actualizada", coordenada) : ApiResponse.notFound("Coordenada"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(coordenadaService.deleteCoordenada(id) ?
                    ApiResponse.success("Coordenada eliminada") : ApiResponse.notFound("Coordenada"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }
}