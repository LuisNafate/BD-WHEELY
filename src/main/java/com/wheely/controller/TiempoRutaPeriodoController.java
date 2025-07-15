package com.wheely.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.wheely.model.TiempoRutaPeriodo;
import com.wheely.service.TiempoRutaPeriodoService;
import com.wheely.util.ApiResponse;

import java.sql.SQLException;
import java.util.List;

public class TiempoRutaPeriodoController {
    private final TiempoRutaPeriodoService tiempoRutaPeriodoService;

    public TiempoRutaPeriodoController(TiempoRutaPeriodoService tiempoRutaPeriodoService) {
        this.tiempoRutaPeriodoService = tiempoRutaPeriodoService;
    }

    public void getAll(Context ctx) {
        try {
            List<TiempoRutaPeriodo> tiempos = tiempoRutaPeriodoService.getAllTiempos();
            ctx.json(ApiResponse.success("Tiempos obtenidos", tiempos));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener tiempos"));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TiempoRutaPeriodo tiempo = tiempoRutaPeriodoService.getTiempoById(id);

            if (tiempo != null) {
                ctx.json(ApiResponse.success("Tiempo encontrado", tiempo));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Tiempo ruta-periodo"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID no válido"));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener tiempo"));
        }
    }

    public void getByRuta(Context ctx) {
        try {
            int idRuta = Integer.parseInt(ctx.pathParam("idRuta"));
            List<TiempoRutaPeriodo> tiempos = tiempoRutaPeriodoService.getTiemposByRuta(idRuta);
            ctx.json(ApiResponse.success("Tiempos de ruta obtenidos", tiempos));
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("Error al obtener tiempos de ruta"));
        }
    }

    public void getByPeriodo(Context ctx) {
        try {
            int idPeriodo = Integer.parseInt(ctx.pathParam("idPeriodo"));
            List<TiempoRutaPeriodo> tiempos = tiempoRutaPeriodoService.getTiemposByPeriodo(idPeriodo);
            ctx.json(ApiResponse.success("Tiempos de periodo obtenidos", tiempos));
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("Error al obtener tiempos de periodo"));
        }
    }

    public void getByRutaAndPeriodo(Context ctx) {
        try {
            int idRuta = Integer.parseInt(ctx.pathParam("idRuta"));
            int idPeriodo = Integer.parseInt(ctx.pathParam("idPeriodo"));
            TiempoRutaPeriodo tiempo = tiempoRutaPeriodoService.getTiempoByRutaAndPeriodo(idRuta, idPeriodo);

            if (tiempo != null) {
                ctx.json(ApiResponse.success("Tiempo específico encontrado", tiempo));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.error("No se encontró tiempo para esa combinación"));
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("Error al obtener tiempo específico"));
        }
    }

    public void create(Context ctx) {
        try {
            TiempoRutaPeriodo tiempo = ctx.bodyAsClass(TiempoRutaPeriodo.class);
            int id = tiempoRutaPeriodoService.createTiempo(tiempo);
            tiempo.setIdTiempoRutaPeriodo(id);

            ctx.status(HttpStatus.CREATED)
                    .json(ApiResponse.success("Tiempo ruta-periodo creado", tiempo));
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("Error al crear tiempo ruta-periodo"));
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TiempoRutaPeriodo tiempo = ctx.bodyAsClass(TiempoRutaPeriodo.class);
            tiempo.setIdTiempoRutaPeriodo(id);

            boolean updated = tiempoRutaPeriodoService.updateTiempo(tiempo);
            if (updated) {
                ctx.json(ApiResponse.success("Tiempo ruta-periodo actualizado", tiempo));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Tiempo ruta-periodo"));
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("Error al actualizar tiempo ruta-periodo"));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = tiempoRutaPeriodoService.deleteTiempo(id);

            if (deleted) {
                ctx.json(ApiResponse.success("Tiempo ruta-periodo eliminado"));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Tiempo ruta-periodo"));
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("Error al eliminar tiempo ruta-periodo"));
        }
    }
}