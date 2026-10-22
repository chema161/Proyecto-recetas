package org.salesianostriana.dam.Dto;

import org.salesianostriana.dam.modelos.Ingrediente;

public record GetIngredienteDto(
        Long id,
        String nombre
) {

    public static GetIngredienteDto of(Ingrediente ingrediente) {
        return new GetIngredienteDto(ingrediente.getId(), ingrediente.getNombre());
    }
}