package com.wheely.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.wheely.service.CoordenadaParadaService;
import com.wheely.util.ApiResponse;

public class CoordenadaParadaController {
    private final CoordenadaParadaService coordenadaParadaService;

    public CoordenadaParadaController(CoordenadaParadaService coordenadaParadaService) {
        this.coordenadaParadaService = coordenadaParadaService;
    }

    public void getAll(Context ctx) {
        try {
            ctx.json(ApiResponse.success("Coordenadas parada obtenidas", coordenadaParadaService.getAllCoordenadaParadas()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var coordenadaParada = coordenadaParadaService.getCoordenadaParadaById(id);
            ctx.json(coordenadaParada != null ? ApiResponse.success("Coordenada parada encontrada", coordenadaParada) : ApiResponse.notFound("Coordenada parada"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void create(Context ctx) {
        try {
            var coordenadaParada = ctx.bodyAsClass(com.wheely.model.CoordenadaParada.class);
            int id = coordenadaParadaService.createCoordenadaParada(coordenadaParada);
            coordenadaParada.setIdCoordenadaParada(id);
            ctx.status(HttpStatus.CREATED).json(ApiResponse.success("Coordenada parada creada", coordenadaParada));
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ApiResponse.error("Error"));
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var coordenadaParada = ctx.bodyAsClass(com.wheely.model.CoordenadaParada.class);
            coordenadaParada.setIdCoordenadaParada(id);
            ctx.json(coordenadaParadaService.updateCoordenadaParada(coordenadaParada) ?
                    ApiResponse.success("Coordenada parada actualizada", coordenadaParada) : ApiResponse.notFound("Coordenada parada"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(coordenadaParadaService.deleteCoordenadaParada(id) ?
                    ApiResponse.success("Coordenada parada eliminada") : ApiResponse.notFound("Coordenada parada"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }
}