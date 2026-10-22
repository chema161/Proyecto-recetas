package org.salesianostriana.dam.Dto;

import jakarta.validation.constraints.NotNull;

public record AddIngredienteRecetaDto(
        @NotNull(message = "El id del ingrediente es obligatorio")
        Long ingredienteId
) {
}
