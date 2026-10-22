package org.salesianostriana.dam.Service;

import lombok.RequiredArgsConstructor;
import org.salesianostriana.dam.Repository.IngredienteRepository;
import org.salesianostriana.dam.error.IngredienteNoEncontradaException;
import org.salesianostriana.dam.modelos.Ingrediente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredienteService {

    private final IngredienteRepository repository;

    /**
     * - Devuelve todos los ingredientes.
     */
    public List<Ingrediente> findAll() {
        return repository.findAll();
    }

    /**
     * - Busca un ingrediente por id.
     * - Lanza IngredienteNoEncontradaException si no existe.
     */
    public Ingrediente findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IngredienteNoEncontradaException(id));
    }

    /**
     * - Guarda un ingrediente nuevo.
     */
    public Ingrediente save(Ingrediente ingrediente) {
        return repository.save(ingrediente);
    }
}
