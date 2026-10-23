package org.salesianostriana.dam.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.salesianostriana.dam.modelos.Categoria;

@Schema(description = "Datos para crear o editar una categoría")
public record EditCategoriaDto(
        @Schema(description = "Nombre único de la categoría", example = "Postres")
        @NotBlank(message = "{editCategoriaDto.nombre.notBlank}")
        @Size(max = 100, message = "{editCategoriaDto.nombre.size}")
        String nombre,

        @Schema(description = "Descripción opcional", example = "Dulces y repostería")
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
