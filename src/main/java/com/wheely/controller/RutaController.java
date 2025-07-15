package com.wheely.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.wheely.service.RutaService;
import com.wheely.util.ApiResponse;

public class RutaController {
    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    public void getAll(Context ctx) {
        try {
            ctx.json(ApiResponse.success("Rutas obtenidas", rutaService.getAllRutas()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var ruta = rutaService.getRutaById(id);
            ctx.json(ruta != null ? ApiResponse.success("Ruta encontrada", ruta) : ApiResponse.notFound("Ruta"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void create(Context ctx) {
        try {
            var ruta = ctx.bodyAsClass(com.wheely.model.Ruta.class);
            int id = rutaService.createRuta(ruta);
            ruta.setIdRuta(id);
            ctx.status(HttpStatus.CREATED).json(ApiResponse.success("Ruta creada", ruta));
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ApiResponse.error("Error"));
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var ruta = ctx.bodyAsClass(com.wheely.model.Ruta.class);
            ruta.setIdRuta(id);
            ctx.json(rutaService.updateRuta(ruta) ?
                    ApiResponse.success("Ruta actualizada", ruta) : ApiResponse.notFound("Ruta"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(rutaService.deleteRuta(id) ?
                    ApiResponse.success("Ruta eliminada") : ApiResponse.notFound("Ruta"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void buscarPorOrigenDestino(Context ctx) {
        try {
            String origen = ctx.queryParam("origen");
            String destino = ctx.queryParam("destino");
            ctx.json(ApiResponse.success("Rutas encontradas", rutaService.buscarRutasPorOrigenDestino(origen, destino)));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }
}