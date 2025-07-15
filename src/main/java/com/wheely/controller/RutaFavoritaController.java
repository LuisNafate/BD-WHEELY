package com.wheely.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.wheely.service.RutaFavoritaService;
import com.wheely.util.ApiResponse;

public class RutaFavoritaController {
    private final RutaFavoritaService rutaFavoritaService;

    public RutaFavoritaController(RutaFavoritaService rutaFavoritaService) {
        this.rutaFavoritaService = rutaFavoritaService;
    }

    public void getAll(Context ctx) {
        try {
            ctx.json(ApiResponse.success("Favoritas obtenidas", rutaFavoritaService.getAllRutasFavoritas()));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var favorita = rutaFavoritaService.getRutaFavoritaById(id);
            ctx.json(favorita != null ? ApiResponse.success("Favorita encontrada", favorita) : ApiResponse.notFound("Favorita"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void getByUsuario(Context ctx) {
        try {
            int usuarioId = Integer.parseInt(ctx.pathParam("usuarioId"));
            ctx.json(ApiResponse.success("Favoritas del usuario", rutaFavoritaService.getRutasFavoritasByUsuario(usuarioId)));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public void agregarFavorita(Context ctx) {
        try {
            int usuarioId = Integer.parseInt(ctx.pathParam("usuarioId"));
            var request = ctx.bodyAsClass(AgregarFavoritaRequest.class);

            var favorita = new com.wheely.model.RutaFavorita();
            favorita.setIdUsuario(usuarioId);
            favorita.setIdRuta(request.rutaId);

            int id = rutaFavoritaService.createRutaFavorita(favorita);
            favorita.setIdRutaFavorita(id);
            ctx.status(HttpStatus.CREATED).json(ApiResponse.success("Favorita agregada", favorita));
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).json(ApiResponse.error("Error"));
        }
    }

    public void eliminarFavorita(Context ctx) {
        try {
            int usuarioId = Integer.parseInt(ctx.pathParam("usuarioId"));
            int rutaId = Integer.parseInt(ctx.pathParam("rutaId"));
            ctx.json(rutaFavoritaService.deleteRutaFavorita(usuarioId, rutaId) ?
                    ApiResponse.success("Favorita eliminada") : ApiResponse.notFound("Favorita"));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(ApiResponse.error("Error"));
        }
    }

    public static class AgregarFavoritaRequest {
        public int rutaId;
    }
}