package org.salesianostriana.dam.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.salesianostriana.dam.modelos.Categoria;

public record EditCategoriaDto(
        @NotBlank(message = "{editCategoriaDto.nombre.notBlank}")
        @Size(max = 100, message = "{editCategoriaDto.nombre.size}")
        String nombre,

        @Size(max = 255, message = "{editCategoriaDto.descripcion.size}")
        String descripcion
) {

    public Categoria to() {
        return Categoria.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .build();
    }
}
