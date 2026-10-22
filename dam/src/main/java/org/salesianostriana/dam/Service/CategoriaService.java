package org.salesianostriana.dam.Service;

import org.salesianostriana.dam.Repository.CategoriaRepository;
import org.salesianostriana.dam.error.CategoriaNoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.salesianostriana.dam.modelos.Categoria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    /**
     * - Devuelve todas las categorías.
     */
    public List<Categoria> findAll() {
        return repository.findAll();
    }

    /**
     * - Busca una categoría por id.
     * - Lanza CategoriaNoEncontradaException si no existe.
     */
    public Categoria findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CategoriaNoEncontradaException(id));
    }

    /**
     * - Guarda una categoría nueva.
     */
    public Categoria save(Categoria categoria) {
        return repository.save(categoria);
    }

    /**
     * - Busca la categoría y lanza 404 si no existe.
     * - Actualiza sus datos y la guarda.
     */
    public Categoria edit(Long id, Categoria datos) {
        Categoria categoria = findById(id);
        categoria.setNombre(datos.getNombre());
        categoria.setDescripcion(datos.getDescripcion());
        return repository.save(categoria);
    }

    /**
     * - Borra la categoría.
     * - Si no existe, no falla.
     */
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
