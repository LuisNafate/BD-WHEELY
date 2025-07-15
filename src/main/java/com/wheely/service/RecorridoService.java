package com.wheely.service;

import com.wheely.model.Recorrido;
import com.wheely.repository.RecorridoRepository;
import com.wheely.repository.RutaRepository;

import java.sql.SQLException;
import java.util.List;

public class RecorridoService {
    private final RecorridoRepository recorridoRepository;

    public RecorridoService(RecorridoRepository recorridoRepository, RutaRepository rutaRepository) {
        this.recorridoRepository = recorridoRepository;
    }

    public List<Recorrido> getAllRecorridos() throws SQLException {
        return recorridoRepository.findAll();
    }

    public Recorrido getRecorridoById(int id) throws SQLException {
        return recorridoRepository.findById(id);
    }

    public int createRecorrido(Recorrido recorrido) throws SQLException {
        validateRecorrido(recorrido);
        return recorridoRepository.save(recorrido);
    }

    public boolean updateRecorrido(Recorrido recorrido) throws SQLException {
        validateRecorrido(recorrido);
        return recorridoRepository.update(recorrido);
    }

    public boolean deleteRecorrido(int id) throws SQLException {
        return recorridoRepository.delete(id);
    }

    public List<Recorrido> getRecorridosByRuta(int rutaId) throws SQLException {
        return recorridoRepository.findByRuta(rutaId);
    }

    public List<Recorrido> buscarRecorridosPorArchivo(String nombreArchivo) throws SQLException {
        return recorridoRepository.findByNombreArchivo(nombreArchivo);
    }

    public boolean updateEstadoRecorrido(int idRecorrido, boolean activo) throws SQLException {
        return recorridoRepository.updateEstado(idRecorrido, activo);
    }

    private void validateRecorrido(Recorrido recorrido) {
        if (recorrido == null || recorrido.getNombreArchivoGeojson() == null || recorrido.getNombreArchivoGeojson().trim().isEmpty()) {
            throw new IllegalArgumentException("Archivo GeoJSON es requerido");
        }
    }
}