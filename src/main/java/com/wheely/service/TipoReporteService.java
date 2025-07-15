package com.wheely.service;

import com.wheely.model.TipoReporte;
import com.wheely.repository.TipoReporteRepository;
import java.sql.SQLException;
import java.util.List;

public class TipoReporteService {
    private final TipoReporteRepository tipoReporteRepository;

    public TipoReporteService(TipoReporteRepository tipoReporteRepository) {
        this.tipoReporteRepository = tipoReporteRepository;
    }

    public List<TipoReporte> getAllTiposReporte() throws SQLException {
        return tipoReporteRepository.findAll();
    }

    public TipoReporte getTipoReporteById(int id) throws SQLException {
        return tipoReporteRepository.findById(id);
    }

    public int createTipoReporte(TipoReporte tipoReporte) throws SQLException {
        validateTipoReporte(tipoReporte);
        return tipoReporteRepository.save(tipoReporte);
    }

    public boolean updateTipoReporte(TipoReporte tipoReporte) throws SQLException {
        validateTipoReporte(tipoReporte);
        return tipoReporteRepository.update(tipoReporte);
    }

    public boolean deleteTipoReporte(int id) throws SQLException {
        return tipoReporteRepository.delete(id);
    }

    public List<TipoReporte> buscarTiposPorNombre(String nombre) throws SQLException {
        return tipoReporteRepository.findByNombre(nombre);
    }

    private void validateTipoReporte(TipoReporte tipoReporte) {
        if (tipoReporte == null || tipoReporte.getNombreTipo() == null || tipoReporte.getNombreTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre del tipo de reporte es requerido");
        }
    }
}