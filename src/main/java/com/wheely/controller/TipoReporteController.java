package com.wheely.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.wheely.service.TipoReporteService;
import com.wheely.util.ApiResponse;

public class TipoReporteController {
    private final TipoReporteService tipoReporteService;

    public TipoReporteController(TipoReporteService tipoReporteService) {
        this.tipoReporteService = tipoReporteService;
    }

    public void getAll(Context ctx) {
        try {
            ctx.json(ApiResponse.success("Tipos obtenidos", tipoReporteService.getAllTiposReporte()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var tipo = tipoReporteService.getTipoReporteById(id);
            ctx.json(tipo != null ? ApiResponse.success("Tipo encontrado", tipo) : ApiResponse.notFound("Tipo"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void create(Context ctx) {
        try {
            var tipo = ctx.bodyAsClass(com.wheely.model.TipoReporte.class);
            int id = tipoReporteService.createTipoReporte(tipo);
            tipo.setIdTipoReporte(id);
            ctx.status(HttpStatus.CREATED).json(ApiResponse.success("Tipo creado", tipo));
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ApiResponse.error("Error"));
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var tipo = ctx.bodyAsClass(com.wheely.model.TipoReporte.class);
            tipo.setIdTipoReporte(id);
            ctx.json(tipoReporteService.updateTipoReporte(tipo) ?
                    ApiResponse.success("Tipo actualizado", tipo) : ApiResponse.notFound("Tipo"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(tipoReporteService.deleteTipoReporte(id) ?
                    ApiResponse.success("Tipo eliminado") : ApiResponse.notFound("Tipo"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }
}