package org.salesianostriana.dam.error;

public class RecetaNoEncontradaException extends RuntimeException {

    public RecetaNoEncontradaException(Long id) {
        super("No se ha encontrado la receta con id " + id);
    }
}
