package com.wheely.service;

import com.wheely.model.Reporte;
import com.wheely.repository.ReporteRepository;
import com.wheely.repository.UsuarioRepository;

import java.sql.SQLException;
import java.util.List;

public class ReporteService {
    private final ReporteRepository reporteRepository;
    private final UsuarioRepository usuarioRepository;

    public ReporteService(ReporteRepository reporteRepository, UsuarioRepository usuarioRepository) {
        this.reporteRepository = reporteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Reporte> getAllReportes() throws SQLException {
        return reporteRepository.findAll();
    }

    public Reporte getReporteById(int id) throws SQLException {
        return reporteRepository.findById(id);
    }

    public int createReporte(Reporte reporte) throws SQLException {
        validateReporte(reporte);
        if (usuarioRepository.findById(reporte.getIdUsuario()) == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        return reporteRepository.save(reporte);
    }

    public boolean updateReporte(Reporte reporte) throws SQLException {
        validateReporte(reporte);
        Reporte existente = reporteRepository.findById(reporte.getIdReporte());
        if (existente == null) {
            throw new IllegalArgumentException("Reporte no encontrado");
        }
        if (existente.getIdUsuario() != reporte.getIdUsuario()) {
            throw new IllegalArgumentException("Solo el autor puede editar el reporte");
        }
        return reporteRepository.update(reporte);
    }

    public boolean deleteReporte(int id, int usuarioId) throws SQLException {
        Reporte reporte = reporteRepository.findById(id);
        if (reporte == null) {
            throw new IllegalArgumentException("Reporte no encontrado");
        }
        if (reporte.getIdUsuario() != usuarioId) {
            throw new IllegalArgumentException("Solo el autor puede eliminar el reporte");
        }
        return reporteRepository.delete(id);
    }

    public List<Reporte> getReportesByUsuario(int usuarioId) throws SQLException {
        return reporteRepository.findByUsuario(usuarioId);
    }

    public List<Reporte> getReportesByRuta(int rutaId) throws SQLException {
        return reporteRepository.findByRuta(rutaId);
    }

    private void validateReporte(Reporte reporte) {
        if (reporte == null || reporte.getTitulo() == null || reporte.getDescripcion() == null) {
            throw new IllegalArgumentException("Datos de reporte incompletos");
        }
        if (reporte.getTitulo().length() > 100) {
            throw new IllegalArgumentException("Título muy largo");
        }
        if (reporte.getIdTipoReporte() < 1 || reporte.getIdTipoReporte() > 5) {
            throw new IllegalArgumentException("Tipo de reporte no válido");
        }
    }
}
