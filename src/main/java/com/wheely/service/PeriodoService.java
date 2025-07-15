package com.wheely.service;

import com.wheely.model.Periodo;
import com.wheely.repository.PeriodoRepository;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

/**
 * Servicio para la lógica de negocio de los periodos
 * Contiene las reglas de validación y operaciones complejas
 */
public class PeriodoService {
    private final PeriodoRepository periodoRepository;

    public PeriodoService(PeriodoRepository periodoRepository) {
        this.periodoRepository = periodoRepository;
    }

    /**
     * Obtiene todos los periodos ordenados por hora de inicio
     * @return Lista de periodos
     * @throws SQLException Si hay error en la consulta
     */
    public List<Periodo> getAllPeriodos() throws SQLException {
        return periodoRepository.findAll();
    }

    /**
     * Obtiene un periodo por su ID
     * @param id ID del periodo
     * @return Periodo encontrado o null
     * @throws SQLException Si hay error en la consulta
     */
    public Periodo getPeriodoById(int id) throws SQLException {
        return periodoRepository.findById(id);
    }

    /**
     * Obtiene un periodo por su nombre
     * @param nombre Nombre del periodo
     * @return Periodo encontrado o null
     * @throws SQLException Si hay error en la consulta
     */
    public Periodo getPeriodoByNombre(String nombre) throws SQLException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del periodo no puede estar vacío");
        }
        return periodoRepository.findByNombre(nombre.trim());
    }

    /**
     * Crea un nuevo periodo
     * @param periodo Periodo a crear
     * @return ID del periodo creado
     * @throws SQLException Si hay error en la consulta
     */
    public int createPeriodo(Periodo periodo) throws SQLException {
        validatePeriodo(periodo);

        // Verificar que no exista un periodo con el mismo nombre
        if (periodoRepository.nombreExists(periodo.getNombrePeriodo())) {
            throw new IllegalArgumentException("Ya existe un periodo con ese nombre");
        }

        // Validar que no haya solapamiento de horarios
        validateNoOverlap(periodo);

        return periodoRepository.save(periodo);
    }

    /**
     * Actualiza un periodo existente
     * @param periodo Periodo con datos actualizados
     * @return true si se actualizó correctamente
     * @throws SQLException Si hay error en la consulta
     */
    public boolean updatePeriodo(Periodo periodo) throws SQLException {
        validatePeriodo(periodo);

        // Verificar que el periodo existe
        Periodo existente = periodoRepository.findById(periodo.getIdPeriodo());
        if (existente == null) {
            throw new IllegalArgumentException("Periodo no encontrado");
        }

        // Verificar que no exista otro periodo con el mismo nombre
        Periodo periodoConMismoNombre = periodoRepository.findByNombre(periodo.getNombrePeriodo());
        if (periodoConMismoNombre != null && periodoConMismoNombre.getIdPeriodo() != periodo.getIdPeriodo()) {
            throw new IllegalArgumentException("Ya existe otro periodo con ese nombre");
        }

        // Validar que no haya solapamiento de horarios con otros periodos
        validateNoOverlapForUpdate(periodo);

        return periodoRepository.update(periodo);
    }

    /**
     * Elimina un periodo
     * @param id ID del periodo a eliminar
     * @return true si se eliminó correctamente
     * @throws SQLException Si hay error en la consulta
     */
    public boolean deletePeriodo(int id) throws SQLException {
        Periodo periodo = periodoRepository.findById(id);
        if (periodo == null) {
            throw new IllegalArgumentException("Periodo no encontrado");
        }

        return periodoRepository.delete(id);
    }

    /**
     * Obtiene el periodo actual basado en la hora del sistema
     * @return Periodo actual o null si no se encuentra
     * @throws SQLException Si hay error en la consulta
     */
    public Periodo getPeriodoActual() throws SQLException {
        return periodoRepository.findPeriodoActual();
    }

    /**
     * Valida los datos básicos de un periodo
     * @param periodo Periodo a validar
     */
    private void validatePeriodo(Periodo periodo) {
        if (periodo == null) {
            throw new IllegalArgumentException("El periodo no puede ser nulo");
        }

        if (periodo.getNombrePeriodo() == null || periodo.getNombrePeriodo().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del periodo es obligatorio");
        }

        if (periodo.getNombrePeriodo().length() > 20) {
            throw new IllegalArgumentException("El nombre del periodo no puede exceder 20 caracteres");
        }

        if (periodo.getHoraInicio() == null) {
            throw new IllegalArgumentException("La hora de inicio es obligatoria");
        }

        if (periodo.getHoraFin() == null) {
            throw new IllegalArgumentException("La hora de fin es obligatoria");
        }

        if (periodo.getDescripcion() != null && periodo.getDescripcion().length() > 100) {
            throw new IllegalArgumentException("La descripción no puede exceder 100 caracteres");
        }
    }

    /**
     * Valida que no haya solapamiento de horarios con otros periodos existentes
     * @param nuevoPeriodo Periodo a validar
     * @throws SQLException Si hay error en la consulta
     */
    private void validateNoOverlap(Periodo nuevoPeriodo) throws SQLException {
        List<Periodo> periodosExistentes = periodoRepository.findAll();

        for (Periodo existente : periodosExistentes) {
            if (hasTimeOverlap(nuevoPeriodo.getHoraInicio(), nuevoPeriodo.getHoraFin(),
                    existente.getHoraInicio(), existente.getHoraFin())) {
                throw new IllegalArgumentException("El horario se solapa con el periodo: " + existente.getNombrePeriodo());
            }
        }
    }

    /**
     * Valida que no haya solapamiento de horarios para actualización
     * @param periodoActualizado Periodo a validar
     * @throws SQLException Si hay error en la consulta
     */
    private void validateNoOverlapForUpdate(Periodo periodoActualizado) throws SQLException {
        List<Periodo> periodosExistentes = periodoRepository.findAll();

        for (Periodo existente : periodosExistentes) {
            // Saltar el mismo periodo que se está actualizando
            if (existente.getIdPeriodo() == periodoActualizado.getIdPeriodo()) {
                continue;
            }

            if (hasTimeOverlap(periodoActualizado.getHoraInicio(), periodoActualizado.getHoraFin(),
                    existente.getHoraInicio(), existente.getHoraFin())) {
                throw new IllegalArgumentException("El horario se solapa con el periodo: " + existente.getNombrePeriodo());
            }
        }
    }

    /**
     * Verifica si dos rangos de tiempo se solapan
     * @param inicio1 Hora de inicio del primer rango
     * @param fin1 Hora de fin del primer rango
     * @param inicio2 Hora de inicio del segundo rango
     * @param fin2 Hora de fin del segundo rango
     * @return true si hay solapamiento
     */
    private boolean hasTimeOverlap(LocalTime inicio1, LocalTime fin1, LocalTime inicio2, LocalTime fin2) {
        // Manejar casos donde el periodo cruza medianoche
        boolean periodo1CrossesMidnight = fin1.isBefore(inicio1);
        boolean periodo2CrossesMidnight = fin2.isBefore(inicio2);

        if (!periodo1CrossesMidnight && !periodo2CrossesMidnight) {
            // Ningún periodo cruza medianoche
            return !(fin1.isBefore(inicio2) || fin2.isBefore(inicio1));
        } else if (periodo1CrossesMidnight && !periodo2CrossesMidnight) {
            // Solo el primer periodo cruza medianoche
            return !(fin1.isBefore(inicio2) && fin2.isBefore(inicio1));
        } else if (!periodo1CrossesMidnight && periodo2CrossesMidnight) {
            // Solo el segundo periodo cruza medianoche
            return !(fin2.isBefore(inicio1) && fin1.isBefore(inicio2));
        } else {
            // Ambos periodos cruzan medianoche - siempre se solapan
            return true;
        }
    }
}