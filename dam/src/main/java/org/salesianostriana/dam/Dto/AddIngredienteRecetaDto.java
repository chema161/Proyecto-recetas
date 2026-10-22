package org.salesianostriana.dam.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddIngredienteRecetaDto(
        @NotNull(message = "{addIngredienteRecetaDto.ingredienteId.notNull}")
        @Positive(message = "{addIngredienteRecetaDto.ingredienteId.positive}")
        Long ingredienteId
) {
}
