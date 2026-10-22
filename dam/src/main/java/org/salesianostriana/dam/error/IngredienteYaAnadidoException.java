package org.salesianostriana.dam.error;

public class IngredienteYaAnadidoException extends RuntimeException {

    public IngredienteYaAnadidoException(Long recetaId, Long ingredienteId) {
        super("La receta con id %d ya contiene el ingrediente con id %d"
                .formatted(recetaId, ingredienteId));
    }
}
