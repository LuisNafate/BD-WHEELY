package com.wheely.service;

import com.wheely.model.TiempoRutaPeriodo;
import com.wheely.repository.TiempoRutaPeriodoRepository;
import com.wheely.repository.RutaRepository;
import com.wheely.repository.PeriodoRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Servicio para la lógica de negocio de los tiempos de ruta por periodo
 * Contiene las reglas de validación y operaciones complejas
 */
public class TiempoRutaPeriodoService {
    private final TiempoRutaPeriodoRepository tiempoRutaPeriodoRepository;
    private final RutaRepository rutaRepository;
    private final PeriodoRepository periodoRepository;

    public TiempoRutaPeriodoService(TiempoRutaPeriodoRepository tiempoRutaPeriodoRepository,
                                    RutaRepository rutaRepository,
                                    PeriodoRepository periodoRepository) {
        this.tiempoRutaPeriodoRepository = tiempoRutaPeriodoRepository;
        this.rutaRepository = rutaRepository;
        this.periodoRepository = periodoRepository;
    }

    /**
     * Obtiene todos los tiempos de ruta por periodo
     * @return Lista de tiempos
     * @throws SQLException Si hay error en la consulta
     */
    public List<TiempoRutaPeriodo> getAllTiempos() throws SQLException {
        return tiempoRutaPeriodoRepository.findAll();
    }

    /**
     * Obtiene un tiempo por su ID
     * @param id ID del tiempo
     * @return Tiempo encontrado o null
     * @throws SQLException Si hay error en la consulta
     */
    public TiempoRutaPeriodo getTiempoById(int id) throws SQLException {
        return tiempoRutaPeriodoRepository.findById(id);
    }

    /**
     * Obtiene todos los tiempos de una ruta específica
     * @param idRuta ID de la ruta
     * @return Lista de tiempos para la ruta
     * @throws SQLException Si hay error en la consulta
     */
    public List<TiempoRutaPeriodo> getTiemposByRuta(int idRuta) throws SQLException {
        if (idRuta <= 0) {
            throw new IllegalArgumentException("ID de ruta no válido");
        }
        return tiempoRutaPeriodoRepository.findByRuta(idRuta);
    }

    /**
     * Obtiene todos los tiempos de un periodo específico
     * @param idPeriodo ID del periodo
     * @return Lista de tiempos para el periodo
     * @throws SQLException Si hay error en la consulta
     */
    public List<TiempoRutaPeriodo> getTiemposByPeriodo(int idPeriodo) throws SQLException {
        if (idPeriodo <= 0) {
            throw new IllegalArgumentException("ID de periodo no válido");
        }
        return tiempoRutaPeriodoRepository.findByPeriodo(idPeriodo);
    }

    /**
     * Obtiene el tiempo específico para una combinación ruta-periodo
     * @param idRuta ID de la ruta
     * @param idPeriodo ID del periodo
     * @return Tiempo encontrado o null
     * @throws SQLException Si hay error en la consulta
     */
    public TiempoRutaPeriodo getTiempoByRutaAndPeriodo(int idRuta, int idPeriodo) throws SQLException {
        if (idRuta <= 0 || idPeriodo <= 0) {
            throw new IllegalArgumentException("IDs de ruta y periodo deben ser válidos");
        }
        return tiempoRutaPeriodoRepository.findByRutaAndPeriodo(idRuta, idPeriodo);
    }

    /**
     * Crea un nuevo tiempo ruta-periodo
     * @param tiempoRutaPeriodo Tiempo a crear
     * @return ID del tiempo creado
     * @throws SQLException Si hay error en la consulta
     */
    public int createTiempo(TiempoRutaPeriodo tiempoRutaPeriodo) throws SQLException {
        validateTiempoRutaPeriodo(tiempoRutaPeriodo);
        validateReferences(tiempoRutaPeriodo);

        // Verificar que no exista ya esta combinación ruta-periodo
        if (tiempoRutaPeriodoRepository.existsRutaPeriodo(
                tiempoRutaPeriodo.getIdRuta(),
                tiempoRutaPeriodo.getIdPeriodo())) {
            throw new IllegalArgumentException("Ya existe un tiempo para esta combinación de ruta y periodo");
        }

        return tiempoRutaPeriodoRepository.save(tiempoRutaPeriodo);
    }

    /**
     * Actualiza un tiempo ruta-periodo existente
     * @param tiempoRutaPeriodo Tiempo con datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException Si hay error en la consulta
     */
    public boolean updateTiempo(TiempoRutaPeriodo tiempoRutaPeriodo) throws SQLException {
        validateTiempoRutaPeriodo(tiempoRutaPeriodo);
        validateReferences(tiempoRutaPeriodo);

        // Verificar que el tiempo existe
        TiempoRutaPeriodo existente = tiempoRutaPeriodoRepository.findById(tiempoRutaPeriodo.getIdTiempoRutaPeriodo());
        if (existente == null) {
            throw new IllegalArgumentException("Tiempo ruta-periodo no encontrado");
        }

        // Si se cambió la ruta o periodo, verificar que no exista ya esa combinación
        if (existente.getIdRuta() != tiempoRutaPeriodo.getIdRuta() ||
                existente.getIdPeriodo() != tiempoRutaPeriodo.getIdPeriodo()) {

            if (tiempoRutaPeriodoRepository.existsRutaPeriodo(
                    tiempoRutaPeriodo.getIdRuta(),
                    tiempoRutaPeriodo.getIdPeriodo())) {
                throw new IllegalArgumentException("Ya existe un tiempo para esta combinación de ruta y periodo");
            }
        }

        return tiempoRutaPeriodoRepository.update(tiempoRutaPeriodo);
    }

    /**
     * Elimina un tiempo ruta-periodo
     * @param id ID del tiempo a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException Si hay error en la consulta
     */
    public boolean deleteTiempo(int id) throws SQLException {
        TiempoRutaPeriodo tiempo = tiempoRutaPeriodoRepository.findById(id);
        if (tiempo == null) {
            throw new IllegalArgumentException("Tiempo ruta-periodo no encontrado");
        }

        return tiempoRutaPeriodoRepository.delete(id);
    }

    /**
     * Elimina todos los tiempos de una ruta específica
     * @param idRuta ID de la ruta
     * @return true si se eliminaron registros
     * @throws SQLException Si hay error en la consulta
     */
    public boolean deleteTiemposByRuta(int idRuta) throws SQLException {
        if (idRuta <= 0) {
            throw new IllegalArgumentException("ID de ruta no válido");
        }

        return tiempoRutaPeriodoRepository.deleteByRuta(idRuta);
    }

    /**
     * Crea o actualiza el tiempo para una combinación ruta-periodo
     * @param idRuta ID de la ruta
     * @param idPeriodo ID del periodo
     * @param tiempoPromedio Tiempo promedio en minutos
     * @return ID del registro creado o actualizado
     * @throws SQLException Si hay error en la consulta
     */
    public int createOrUpdateTiempo(int idRuta, int idPeriodo, int tiempoPromedio) throws SQLException {
        TiempoRutaPeriodo existente = tiempoRutaPeriodoRepository.findByRutaAndPeriodo(idRuta, idPeriodo);

        if (existente != null) {
            // Actualizar tiempo existente
            existente.setTiempoPromedio(tiempoPromedio);
            tiempoRutaPeriodoRepository.update(existente);
            return existente.getIdTiempoRutaPeriodo();
        } else {
            // Crear nuevo tiempo
            TiempoRutaPeriodo nuevo = new TiempoRutaPeriodo(idRuta, idPeriodo, tiempoPromedio);
            return createTiempo(nuevo);
        }
    }

    /**
     * Obtiene el tiempo promedio actual para una ruta basado en la hora actual
     * @param idRuta ID de la ruta
     * @return Tiempo promedio en minutos o -1 si no se encuentra
     * @throws SQLException Si hay error en la consulta
     */
    public int getTiempoPromedioActual(int idRuta) throws SQLException {
        if (idRuta <= 0) {
            throw new IllegalArgumentException("ID de ruta no válido");
        }

        // Obtener el periodo actual
        var periodoActual = periodoRepository.findPeriodoActual();
        if (periodoActual == null) {
            return -1; // No hay periodo definido para la hora actual
        }

        // Obtener el tiempo para la ruta en el periodo actual
        TiempoRutaPeriodo tiempo = tiempoRutaPeriodoRepository.findByRutaAndPeriodo(idRuta, periodoActual.getIdPeriodo());
        return tiempo != null ? tiempo.getTiempoPromedio() : -1;
    }

    /**
     * Valida los datos básicos de un tiempo ruta-periodo
     * @param tiempoRutaPeriodo Tiempo a validar
     */
    private void validateTiempoRutaPeriodo(TiempoRutaPeriodo tiempoRutaPeriodo) {
        if (tiempoRutaPeriodo == null) {
            throw new IllegalArgumentException("El tiempo ruta-periodo no puede ser nulo");
        }

        if (tiempoRutaPeriodo.getIdRuta() <= 0) {
            throw new IllegalArgumentException("ID de ruta no válido");
        }

        if (tiempoRutaPeriodo.getIdPeriodo() <= 0) {
            throw new IllegalArgumentException("ID de periodo no válido");
        }

        if (tiempoRutaPeriodo.getTiempoPromedio() <= 0) {
            throw new IllegalArgumentException("El tiempo promedio debe ser mayor a 0 minutos");
        }

        if (tiempoRutaPeriodo.getTiempoPromedio() > 300) { // 5 horas máximo
            throw new IllegalArgumentException("El tiempo promedio no puede exceder 300 minutos");
        }
    }

    /**
     * Valida que existan las referencias a ruta y periodo
     * @param tiempoRutaPeriodo Tiempo a validar
     * @throws SQLException Si hay error en la consulta
     */
    private void validateReferences(TiempoRutaPeriodo tiempoRutaPeriodo) throws SQLException {
        // Verificar que existe la ruta
        if (rutaRepository.findById(tiempoRutaPeriodo.getIdRuta()) == null) {
            throw new IllegalArgumentException("La ruta especificada no existe");
        }

        // Verificar que existe el periodo
        if (periodoRepository.findById(tiempoRutaPeriodo.getIdPeriodo()) == null) {
            throw new IllegalArgumentException("El periodo especificado no existe");
        }
    }
}