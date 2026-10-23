package org.salesianostriana.dam.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Ingrediente que se quiere añadir a una receta")
public record AddIngredienteRecetaDto(
        @Schema(description = "Id de un ingrediente existente", example = "1")
        @NotNull(message = "{addIngredienteRecetaDto.ingredienteId.notNull}")
        @Positive(message = "{addIngredienteRecetaDto.ingredienteId.positive}")
        Long ingredienteId
) {
}
