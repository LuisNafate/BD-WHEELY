package com.wheely;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wheely.di.AppModule;

/**
 * Clase principal de la aplicación Wheely
 */
public class Main {
    public static void main(String[] args) {
        // Configurar Jackson para manejar LocalTime y LocalDateTime
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new JavalinJackson());
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> {
                    it.anyHost();
                    it.allowCredentials = true;
                });
            });
        }).start(7000);

        // Registrar todas las rutas
        AppModule.initUsuarios().register(app);
        AppModule.initReportes().register(app);
        AppModule.initTiposReporte().register(app);
        AppModule.initRutas().register(app);
        AppModule.initRecorridos().register(app);
        AppModule.initCoordenadas().register(app);
        AppModule.initParadas().register(app);
        AppModule.initCoordenadasParada().register(app);
        AppModule.initRutasFavoritas().register(app);

        // NUEVAS RUTAS
        AppModule.initPeriodos().register(app);
        AppModule.initTiemposRutaPeriodo().register(app);

        // Ruta de prueba
        app.get("/", ctx -> ctx.result("API Wheely funcionando correctamente"));

        System.out.println("Servidor iniciado en http://localhost:7000");
        System.out.println("Endpoints disponibles:");
        System.out.println("- Usuarios: /api/usuarios");
        System.out.println("- Reportes: /api/reportes");
        System.out.println("- Tipos de Reporte: /api/tipos-reporte");
        System.out.println("- Rutas: /api/rutas");
        System.out.println("- Recorridos: /api/recorridos");
        System.out.println("- Coordenadas: /api/coordenadas");
        System.out.println("- Paradas: /api/paradas");
        System.out.println("- Coordenadas Parada: /api/coordenadas-parada");
        System.out.println("- Rutas Favoritas: /api/rutas-favoritas");
        System.out.println("- Periodos: /api/periodos");
        System.out.println("- Tiempos Ruta-Periodo: /api/tiempos-ruta-periodo");
    }
}