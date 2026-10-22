package org.salesianostriana.dam.Dto;

import org.salesianostriana.dam.modelos.Categoria;

public record GetCategoriaDto(
        Long id,
        String nombre,
        String descripcion
) {

    public static GetCategoriaDto of(Categoria categoria) {
        return new GetCategoriaDto(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }
}
