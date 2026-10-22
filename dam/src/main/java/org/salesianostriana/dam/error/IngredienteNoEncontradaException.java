package org.salesianostriana.dam.error;

public class IngredienteNoEncontradaException extends RuntimeException {

    public IngredienteNoEncontradaException(Long id) {
        super("No se ha encontrado el ingrediente con id " + id);
    }
}
