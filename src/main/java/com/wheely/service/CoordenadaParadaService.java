package com.wheely.service;

import com.wheely.model.CoordenadaParada;
import com.wheely.repository.CoordenadaParadaRepository;
import com.wheely.repository.ParadaRepository;

import java.sql.SQLException;
import java.util.List;

public class CoordenadaParadaService {
    private final CoordenadaParadaRepository coordenadaParadaRepository;

    public CoordenadaParadaService(CoordenadaParadaRepository coordenadaParadaRepository, ParadaRepository paradaRepository) {
        this.coordenadaParadaRepository = coordenadaParadaRepository;
    }

    public List<CoordenadaParada> getAllCoordenadaParadas() throws SQLException {
        return coordenadaParadaRepository.findAll();
    }

    public CoordenadaParada getCoordenadaParadaById(int id) throws SQLException {
        return coordenadaParadaRepository.findById(id);
    }

    public int createCoordenadaParada(CoordenadaParada coordenadaParada) throws SQLException {
        return coordenadaParadaRepository.save(coordenadaParada);
    }

    public boolean updateCoordenadaParada(CoordenadaParada coordenadaParada) throws SQLException {
        return coordenadaParadaRepository.update(coordenadaParada);
    }

    public boolean deleteCoordenadaParada(int id) throws SQLException {
        return coordenadaParadaRepository.delete(id);
    }

    public List<CoordenadaParada> getCoordenadasByParada(int paradaId) throws SQLException {
        return coordenadaParadaRepository.findByParada(paradaId);
    }
}