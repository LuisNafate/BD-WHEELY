package com.wheely.service;

import com.wheely.model.Coordenada;
import com.wheely.repository.CoordenadaRepository;
import com.wheely.repository.RecorridoRepository;

import java.sql.SQLException;
import java.util.List;

public class CoordenadaService {
    private final CoordenadaRepository coordenadaRepository;

    public CoordenadaService(CoordenadaRepository coordenadaRepository, RecorridoRepository recorridoRepository) {
        this.coordenadaRepository = coordenadaRepository;
    }

    public List<Coordenada> getAllCoordenadas() throws SQLException {
        return coordenadaRepository.findAll();
    }

    public Coordenada getCoordenadaById(int id) throws SQLException {
        return coordenadaRepository.findById(id);
    }

    public int createCoordenada(Coordenada coordenada) throws SQLException {
        return coordenadaRepository.save(coordenada);
    }

    public boolean updateCoordenada(Coordenada coordenada) throws SQLException {
        return coordenadaRepository.update(coordenada);
    }

    public boolean deleteCoordenada(int id) throws SQLException {
        return coordenadaRepository.delete(id);
    }

    public List<Coordenada> getCoordenadasByRecorrido(int recorridoId) throws SQLException {
        return coordenadaRepository.findByRecorrido(recorridoId);
    }
}