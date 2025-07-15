package com.wheely.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.wheely.model.Reporte;
import com.wheely.service.ReporteService;
import com.wheely.util.ApiResponse;

import java.sql.SQLException;
import java.util.List;

public class ReporteController {
    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    // GET /api/reportes
    public void getAll(Context ctx) {
        try {
            List<Reporte> reportes = reporteService.getAllReportes();
            ctx.json(ApiResponse.success("Reportes obtenidos", reportes));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener reportes"));
        }
    }

    // GET /api/reportes/{id}
    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Reporte reporte = reporteService.getReporteById(id);
            if (reporte != null) {
                ctx.json(ApiResponse.success("Reporte encontrado", reporte));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Reporte"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID no válido"));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener reporte"));
        }
    }

    // POST /api/reportes
    public void create(Context ctx) {
        try {
            Reporte reporte = ctx.bodyAsClass(Reporte.class);
            int id = reporteService.createReporte(reporte);
            reporte.setIdReporte(id);
            ctx.status(HttpStatus.CREATED)
                    .json(ApiResponse.success("Reporte creado", reporte));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al crear reporte"));
        }
    }

    // PUT /api/reportes/{id}
    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Reporte reporte = ctx.bodyAsClass(Reporte.class);
            reporte.setIdReporte(id);

            boolean updated = reporteService.updateReporte(reporte);
            if (updated) {
                ctx.json(ApiResponse.success("Reporte actualizado", reporte));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Reporte"));
            }
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al actualizar reporte"));
        }
    }

    // DELETE /api/reportes/{id}?usuarioId={usuarioId}
    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            String usuarioIdParam = ctx.queryParam("usuarioId");

            if (usuarioIdParam == null) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(ApiResponse.error("usuarioId requerido"));
                return;
            }

            int usuarioId = Integer.parseInt(usuarioIdParam);
            boolean deleted = reporteService.deleteReporte(id, usuarioId);

            if (deleted) {
                ctx.json(ApiResponse.success("Reporte eliminado"));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Reporte"));
            }
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al eliminar reporte"));
        }
    }

    // GET /api/reportes/usuario/{usuarioId}
    public void getByUsuario(Context ctx) {
        try {
            int usuarioId = Integer.parseInt(ctx.pathParam("usuarioId"));
            List<Reporte> reportes = reporteService.getReportesByUsuario(usuarioId);
            ctx.json(ApiResponse.success("Reportes del usuario", reportes));
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("usuarioId no válido"));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener reportes"));
        }
    }

    // GET /api/reportes/ruta/{rutaId}
    public void getByRuta(Context ctx) {
        try {
            int rutaId = Integer.parseInt(ctx.pathParam("rutaId"));
            List<Reporte> reportes = reporteService.getReportesByRuta(rutaId);
            ctx.json(ApiResponse.success("Reportes de la ruta", reportes));
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("rutaId no válido"));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener reportes"));
        }
    }
}