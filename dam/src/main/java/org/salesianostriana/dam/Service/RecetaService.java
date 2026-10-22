package org.salesianostriana.dam.Service;

import lombok.RequiredArgsConstructor;
import org.salesianostriana.dam.Dto.EditRecetaDto;
import org.salesianostriana.dam.Repository.RecetaRepository;
import org.salesianostriana.dam.error.IngredienteYaAnadidoException;
import org.salesianostriana.dam.error.RecetaNoEncontradaException;
import org.salesianostriana.dam.error.TiempoInvalidoException;
import org.salesianostriana.dam.modelos.Categoria;
import org.salesianostriana.dam.modelos.Ingrediente;
import org.salesianostriana.dam.modelos.Receta;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecetaService {

    private final RecetaRepository repository;
    private final CategoriaService categoriaService;
    private final IngredienteService ingredienteService;

    /**
     * - Devuelve todas las recetas.
     */
    public List<Receta> findAll() {
        return repository.findAll();
    }

    /**
     * - Busca una receta por id.
     * - Lanza RecetaNoEncontradaException si no existe.
     */
    public Receta findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecetaNoEncontradaException(id));
    }

    /**
     * - Valida que el tiempo de preparación sea mayor que 0.
     * - Comprueba que la categoría existe y lanza 404 si no.
     * - Guarda la receta asociada a esa categoría.
     */
    public Receta save(EditRecetaDto dto) {
        validarTiempo(dto.tiempoPreparacionMin());
        Categoria categoria = categoriaService.findById(dto.categoriaId());
        return repository.save(dto.to(categoria));
    }

    /**
     * - Busca la receta y lanza 404 si no existe.
     * - Valida el tiempo y comprueba que la categoría existe.
     * - Actualiza sus datos y la guarda.
     */
    public Receta edit(Long id, EditRecetaDto dto) {
        Receta receta = findById(id);
        validarTiempo(dto.tiempoPreparacionMin());
        Categoria categoria = categoriaService.findById(dto.categoriaId());

        receta.setNombre(dto.nombre());
        receta.setTiempoPreparacionMin(dto.tiempoPreparacionMin());
        receta.setDificultad(dto.dificultad());
        receta.setCategoria(categoria);

        return repository.save(receta);
    }

    /**
     * - Borra la receta.
     * - Si no existe, no falla.
     */
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     * - Busca la receta y lanza 404 si no existe.
     * - Busca el ingrediente y lanza 404 si no existe.
     * - Lanza IngredienteYaAnadidoException si la receta ya lo contiene.
     * - Asocia el ingrediente a la receta y lo guarda.
     */
    @Transactional
    public Receta addIngrediente(Long recetaId, Long ingredienteId) {
        Receta receta = findById(recetaId);
        Ingrediente ingrediente = ingredienteService.findById(ingredienteId);

        boolean yaAnadido = receta.getIngredientes().stream()
                .anyMatch(i -> i.getId().equals(ingredienteId));
        if (yaAnadido) {
            throw new IngredienteYaAnadidoException(recetaId, ingredienteId);
        }

        receta.addIngrediente(ingrediente);
        return repository.save(receta);
    }

    private void validarTiempo(Integer tiempo) {
        if (tiempo == null || tiempo <= 0) {
            throw new TiempoInvalidoException(tiempo);
        }
    }
}