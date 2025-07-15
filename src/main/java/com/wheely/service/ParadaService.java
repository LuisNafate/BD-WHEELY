package com.wheely.service;

import com.wheely.model.Parada;
import com.wheely.repository.ParadaRepository;
import com.wheely.repository.RecorridoRepository;

import java.sql.SQLException;
import java.util.List;

public class ParadaService {
    private final ParadaRepository paradaRepository;

    public ParadaService(ParadaRepository paradaRepository, RecorridoRepository recorridoRepository) {
        this.paradaRepository = paradaRepository;
    }

    public List<Parada> getAllParadas() throws SQLException {
        return paradaRepository.findAll();
    }

    public Parada getParadaById(int id) throws SQLException {
        return paradaRepository.findById(id);
    }

    public Parada createParada(Parada parada) throws SQLException {
        int id = paradaRepository.save(parada);
        parada.setIdParada(id);
        return parada;
    }

    public boolean updateParada(Parada parada) throws SQLException {
        return paradaRepository.update(parada);
    }

    public boolean deleteParada(int id) throws SQLException {
        return paradaRepository.delete(id);
    }
}