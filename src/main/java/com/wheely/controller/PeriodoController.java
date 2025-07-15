package com.wheely.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import com.wheely.model.Periodo;
import com.wheely.service.PeriodoService;
import com.wheely.util.ApiResponse;

import java.sql.SQLException;
import java.util.List;

/**
 * Controlador REST para la gestión de periodos
 * Maneja las peticiones HTTP relacionadas con los periodos del día
 */
public class PeriodoController {
    private final PeriodoService periodoService;

    public PeriodoController(PeriodoService periodoService) {
        this.periodoService = periodoService;
    }

    /**
     * GET /api/periodos
     * Obtiene todos los periodos
     */
    public void getAll(Context ctx) {
        try {
            List<Periodo> periodos = periodoService.getAllPeriodos();
            ctx.json(ApiResponse.success("Periodos obtenidos", periodos));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener periodos"));
        }
    }

    /**
     * GET /api/periodos/{id}
     * Obtiene un periodo por su ID
     */
    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Periodo periodo = periodoService.getPeriodoById(id);

            if (periodo != null) {
                ctx.json(ApiResponse.success("Periodo encontrado", periodo));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Periodo"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID no válido"));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener periodo"));
        }
    }

    /**
     * GET /api/periodos/nombre/{nombre}
     * Obtiene un periodo por su nombre
     */
    public void getByNombre(Context ctx) {
        try {
            String nombre = ctx.pathParam("nombre");
            Periodo periodo = periodoService.getPeriodoByNombre(nombre);

            if (periodo != null) {
                ctx.json(ApiResponse.success("Periodo encontrado", periodo));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Periodo"));
            }
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener periodo"));
        }
    }

    /**
     * GET /api/periodos/actual
     * Obtiene el periodo actual basado en la hora del sistema
     */
    public void getActual(Context ctx) {
        try {
            Periodo periodoActual = periodoService.getPeriodoActual();

            if (periodoActual != null) {
                ctx.json(ApiResponse.success("Periodo actual encontrado", periodoActual));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.error("No se encontró periodo para la hora actual"));
            }
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al obtener periodo actual"));
        }
    }

    /**
     * POST /api/periodos
     * Crea un nuevo periodo
     */
    public void create(Context ctx) {
        try {
            Periodo periodo = ctx.bodyAsClass(Periodo.class);
            int id = periodoService.createPeriodo(periodo);
            periodo.setIdPeriodo(id);

            ctx.status(HttpStatus.CREATED)
                    .json(ApiResponse.success("Periodo creado", periodo));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al crear periodo"));
        }
    }

    /**
     * PUT /api/periodos/{id}
     * Actualiza un periodo existente
     */
    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Periodo periodo = ctx.bodyAsClass(Periodo.class);
            periodo.setIdPeriodo(id);

            boolean updated = periodoService.updatePeriodo(periodo);
            if (updated) {
                ctx.json(ApiResponse.success("Periodo actualizado", periodo));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Periodo"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID no válido"));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al actualizar periodo"));
        }
    }

    /**
     * DELETE /api/periodos/{id}
     * Elimina un periodo
     */
    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = periodoService.deletePeriodo(id);

            if (deleted) {
                ctx.json(ApiResponse.success("Periodo eliminado"));
            } else {
                ctx.status(HttpStatus.NOT_FOUND)
                        .json(ApiResponse.notFound("Periodo"));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error("ID no válido"));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST)
                    .json(ApiResponse.error(e.getMessage()));
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .json(ApiResponse.error("Error al eliminar periodo"));
        }
    }
}