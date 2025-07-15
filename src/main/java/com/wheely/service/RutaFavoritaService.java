package com.wheely.service;

import com.wheely.model.RutaFavorita;
import com.wheely.repository.RutaFavoritaRepository;
import com.wheely.repository.UsuarioRepository;
import com.wheely.repository.RutaRepository;
import java.sql.SQLException;
import java.util.List;

public class RutaFavoritaService {
    private final RutaFavoritaRepository rutaFavoritaRepository;
    private final UsuarioRepository usuarioRepository;
    private final RutaRepository rutaRepository;

    public RutaFavoritaService(RutaFavoritaRepository rutaFavoritaRepository,
                               UsuarioRepository usuarioRepository,
                               RutaRepository rutaRepository) {
        this.rutaFavoritaRepository = rutaFavoritaRepository;
        this.usuarioRepository = usuarioRepository;
        this.rutaRepository = rutaRepository;
    }

    public List<RutaFavorita> getAllRutasFavoritas() throws SQLException {
        return rutaFavoritaRepository.findAll();
    }

    public RutaFavorita getRutaFavoritaById(int id) throws SQLException {
        return rutaFavoritaRepository.findById(id);
    }

    public List<RutaFavorita> getRutasFavoritasByUsuario(int usuarioId) throws SQLException {
        return rutaFavoritaRepository.findByUsuario(usuarioId);
    }

    public List<RutaFavorita> getFavoritasByRuta(int rutaId) throws SQLException {
        return rutaFavoritaRepository.findByRuta(rutaId);
    }

    public int createRutaFavorita(RutaFavorita rutaFavorita) throws SQLException {
        validateRutaFavorita(rutaFavorita);

        // Verificar que no exista ya la combinación usuario-ruta
        if (rutaFavoritaRepository.existsByUsuarioAndRuta(rutaFavorita.getIdUsuario(), rutaFavorita.getIdRuta())) {
            throw new IllegalArgumentException("La ruta ya está en favoritos");
        }

        return rutaFavoritaRepository.save(rutaFavorita);
    }

    public boolean deleteRutaFavorita(int usuarioId, int rutaId) throws SQLException {
        return rutaFavoritaRepository.deleteByUsuarioAndRuta(usuarioId, rutaId);
    }

    public boolean existeFavorita(int usuarioId, int rutaId) throws SQLException {
        return rutaFavoritaRepository.existsByUsuarioAndRuta(usuarioId, rutaId);
    }

    private void validateRutaFavorita(RutaFavorita rutaFavorita) throws SQLException {
        if (usuarioRepository.findById(rutaFavorita.getIdUsuario()) == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        if (rutaRepository.findById(rutaFavorita.getIdRuta()) == null) {
            throw new IllegalArgumentException("Ruta no encontrada");
        }
    }
}