package com.wheely.service;

import com.wheely.model.Ruta;
import com.wheely.repository.RutaRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Servicio para la lógica de negocio de las rutas
 * Contiene las reglas de validación y operaciones complejas
 * Actualizado para nueva estructura sin tiempo_promedio
 */
public class RutaService {
    private final RutaRepository rutaRepository;

    public RutaService(RutaRepository rutaRepository) {
        this.rutaRepository = rutaRepository;
    }

    /**
     * Obtiene todas las rutas
     * @return Lista de rutas
     * @throws SQLException Si hay error en la consulta
     */
    public List<Ruta> getAllRutas() throws SQLException {
        return rutaRepository.findAll();
    }

    /**
     * Obtiene una ruta por su ID
     * @param id ID de la ruta
     * @return Ruta encontrada o null
     * @throws SQLException Si hay error en la consulta
     */
    public Ruta getRutaById(int id) throws SQLException {
        return rutaRepository.findById(id);
    }

    /**
     * Crea una nueva ruta
     * @param ruta Ruta a crear
     * @return ID de la ruta creada
     * @throws SQLException Si hay error en la consulta
     */
    public int createRuta(Ruta ruta) throws SQLException {
        validateRuta(ruta);

        // Verificar que no exista una ruta con el mismo nombre
        if (rutaRepository.existsByNombre(ruta.getNombreRuta())) {
            throw new IllegalArgumentException("Ya existe una ruta con ese nombre");
        }

        return rutaRepository.save(ruta);
    }

    /**
     * Actualiza una ruta existente
     * @param ruta Ruta con datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException Si hay error en la consulta
     */
    public boolean updateRuta(Ruta ruta) throws SQLException {
        validateRuta(ruta);

        // Verificar que la ruta existe
        Ruta existente = rutaRepository.findById(ruta.getIdRuta());
        if (existente == null) {
            throw new IllegalArgumentException("Ruta no encontrada");
        }

        // Verificar que no exista otra ruta con el mismo nombre
        if (rutaRepository.existsByNombreExcludingId(ruta.getNombreRuta(), ruta.getIdRuta())) {
            throw new IllegalArgumentException("Ya existe otra ruta con ese nombre");
        }

        return rutaRepository.update(ruta);
    }

    /**
     * Elimina una ruta
     * @param id ID de la ruta a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException Si hay error en la consulta
     */
    public boolean deleteRuta(int id) throws SQLException {
        Ruta ruta = rutaRepository.findById(id);
        if (ruta == null) {
            throw new IllegalArgumentException("Ruta no encontrada");
        }

        return rutaRepository.delete(id);
    }

    /**
     * Busca rutas por nombre
     * @param nombre Nombre o parte del nombre
     * @return Lista de rutas que coinciden
     * @throws SQLException Si hay error en la consulta
     */
    public List<Ruta> searchByNombre(String nombre) throws SQLException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de búsqueda no puede estar vacío");
        }
        return rutaRepository.findByNombre(nombre.trim());
    }

    /**
     * Busca rutas por origen
     * @param origen Origen o parte del origen
     * @return Lista de rutas que coinciden
     * @throws SQLException Si hay error en la consulta
     */
    public List<Ruta> searchByOrigen(String origen) throws SQLException {
        if (origen == null || origen.trim().isEmpty()) {
            throw new IllegalArgumentException("El origen de búsqueda no puede estar vacío");
        }
        return rutaRepository.findByOrigen(origen.trim());
    }

    /**
     * Busca rutas por destino
     * @param destino Destino o parte del destino
     * @return Lista de rutas que coinciden
     * @throws SQLException Si hay error en la consulta
     */
    public List<Ruta> searchByDestino(String destino) throws SQLException {
        if (destino == null || destino.trim().isEmpty()) {
            throw new IllegalArgumentException("El destino de búsqueda no puede estar vacío");
        }
        return rutaRepository.findByDestino(destino.trim());
    }

    /**
     * Obtiene el número total de rutas
     * @return Número total de rutas
     * @throws SQLException Si hay error en la consulta
     */
    public int getTotalRutas() throws SQLException {
        return rutaRepository.count();
    }

    // MÉTODOS COMPATIBLES CON CONTROLADOR EXISTENTE
    public List<Ruta> buscarRutasPorNombre(String nombre) throws SQLException {
        return searchByNombre(nombre);
    }

    public List<Ruta> buscarRutasPorOrigen(String origen) throws SQLException {
        return searchByOrigen(origen);
    }

    public List<Ruta> buscarRutasPorDestino(String destino) throws SQLException {
        return searchByDestino(destino);
    }

    public List<Ruta> buscarRutasPorOrigenDestino(String origen, String destino) throws SQLException {
        List<Ruta> todasLasRutas = rutaRepository.findAll();
        return todasLasRutas.stream()
                .filter(ruta -> (origen == null || origen.trim().isEmpty() ||
                        ruta.getOrigen().toLowerCase().contains(origen.toLowerCase())) &&
                        (destino == null || destino.trim().isEmpty() ||
                                ruta.getDestino().toLowerCase().contains(destino.toLowerCase())))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Valida los datos básicos de una ruta
     * @param ruta Ruta a validar
     */
    private void validateRuta(Ruta ruta) {
        if (ruta == null) {
            throw new IllegalArgumentException("La ruta no puede ser nula");
        }

        if (ruta.getNombreRuta() == null || ruta.getNombreRuta().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la ruta es obligatorio");
        }

        if (ruta.getNombreRuta().length() > 100) {
            throw new IllegalArgumentException("El nombre de la ruta no puede exceder 100 caracteres");
        }

        if (ruta.getOrigen() == null || ruta.getOrigen().trim().isEmpty()) {
            throw new IllegalArgumentException("El origen es obligatorio");
        }

        if (ruta.getOrigen().length() > 100) {
            throw new IllegalArgumentException("El origen no puede exceder 100 caracteres");
        }

        if (ruta.getDestino() == null || ruta.getDestino().trim().isEmpty()) {
            throw new IllegalArgumentException("El destino es obligatorio");
        }

        if (ruta.getDestino().length() > 100) {
            throw new IllegalArgumentException("El destino no puede exceder 100 caracteres");
        }

        // Limpiar espacios en blanco
        ruta.setNombreRuta(ruta.getNombreRuta().trim());
        ruta.setOrigen(ruta.getOrigen().trim());
        ruta.setDestino(ruta.getDestino().trim());
    }
}