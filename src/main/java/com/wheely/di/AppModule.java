package com.wheely.di;

import com.wheely.controller.*;
import com.wheely.repository.*;
import com.wheely.routes.*;
import com.wheely.service.*;

/**
 * Módulo de configuración de dependencias de la aplicación
 */
public class AppModule {

    public static UsuarioRoutes initUsuarios() {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);
        UsuarioController usuarioController = new UsuarioController(usuarioService);
        return new UsuarioRoutes(usuarioController);
    }

    public static ReporteRoutes initReportes() {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        ReporteRepository reporteRepository = new ReporteRepository();
        ReporteService reporteService = new ReporteService(reporteRepository, usuarioRepository);
        ReporteController reporteController = new ReporteController(reporteService);
        return new ReporteRoutes(reporteController);
    }

    public static TipoReporteRoutes initTiposReporte() {
        TipoReporteRepository tipoReporteRepository = new TipoReporteRepository();
        TipoReporteService tipoReporteService = new TipoReporteService(tipoReporteRepository);
        TipoReporteController tipoReporteController = new TipoReporteController(tipoReporteService);
        return new TipoReporteRoutes(tipoReporteController);
    }

    public static RutaRoutes initRutas() {
        RutaRepository rutaRepository = new RutaRepository();
        RutaService rutaService = new RutaService(rutaRepository);
        RutaController rutaController = new RutaController(rutaService);
        return new RutaRoutes(rutaController);
    }

    public static RecorridoRoutes initRecorridos() {
        RutaRepository rutaRepository = new RutaRepository();
        RecorridoRepository recorridoRepository = new RecorridoRepository();
        RecorridoService recorridoService = new RecorridoService(recorridoRepository, rutaRepository);
        RecorridoController recorridoController = new RecorridoController(recorridoService);
        return new RecorridoRoutes(recorridoController);
    }

    public static CoordenadaRoutes initCoordenadas() {
        RecorridoRepository recorridoRepository = new RecorridoRepository();
        CoordenadaRepository coordenadaRepository = new CoordenadaRepository();
        CoordenadaService coordenadaService = new CoordenadaService(coordenadaRepository, recorridoRepository);
        CoordenadaController coordenadaController = new CoordenadaController(coordenadaService);
        return new CoordenadaRoutes(coordenadaController);
    }

    public static ParadaRoutes initParadas() {
        RecorridoRepository recorridoRepository = new RecorridoRepository();
        ParadaRepository paradaRepository = new ParadaRepository();
        ParadaService paradaService = new ParadaService(paradaRepository, recorridoRepository);
        ParadaController paradaController = new ParadaController(paradaService);
        return new ParadaRoutes(paradaController);
    }

    public static CoordenadaParadaRoutes initCoordenadasParada() {
        ParadaRepository paradaRepository = new ParadaRepository();
        CoordenadaParadaRepository coordenadaParadaRepository = new CoordenadaParadaRepository();
        CoordenadaParadaService coordenadaParadaService = new CoordenadaParadaService(coordenadaParadaRepository, paradaRepository);
        CoordenadaParadaController coordenadaParadaController = new CoordenadaParadaController(coordenadaParadaService);
        return new CoordenadaParadaRoutes(coordenadaParadaController);
    }

    public static RutaFavoritaRoutes initRutasFavoritas() {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        RutaRepository rutaRepository = new RutaRepository();
        RutaFavoritaRepository rutaFavoritaRepository = new RutaFavoritaRepository();
        RutaFavoritaService rutaFavoritaService = new RutaFavoritaService(rutaFavoritaRepository, usuarioRepository, rutaRepository);
        RutaFavoritaController rutaFavoritaController = new RutaFavoritaController(rutaFavoritaService);
        return new RutaFavoritaRoutes(rutaFavoritaController);
    }

    // NUEVOS MÉTODOS PARA PERIODO Y TIEMPORUTA
    public static PeriodoRoutes initPeriodos() {
        PeriodoRepository periodoRepository = new PeriodoRepository();
        PeriodoService periodoService = new PeriodoService(periodoRepository);
        PeriodoController periodoController = new PeriodoController(periodoService);
        return new PeriodoRoutes(periodoController);
    }

    public static TiempoRutaPeriodoRoutes initTiemposRutaPeriodo() {
        TiempoRutaPeriodoRepository tiempoRutaPeriodoRepository = new TiempoRutaPeriodoRepository();
        RutaRepository rutaRepository = new RutaRepository();
        PeriodoRepository periodoRepository = new PeriodoRepository();
        TiempoRutaPeriodoService tiempoRutaPeriodoService = new TiempoRutaPeriodoService(
                tiempoRutaPeriodoRepository,
                rutaRepository,
                periodoRepository
        );
        TiempoRutaPeriodoController tiempoRutaPeriodoController = new TiempoRutaPeriodoController(tiempoRutaPeriodoService);
        return new TiempoRutaPeriodoRoutes(tiempoRutaPeriodoController);
    }
}