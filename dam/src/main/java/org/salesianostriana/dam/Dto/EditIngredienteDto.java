package org.salesianostriana.dam.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.salesianostriana.dam.modelos.Ingrediente;

public record EditIngredienteDto(
        @NotBlank(message = "{editIngredienteDto.nombre.notBlank}")
        @Size(max = 100, message = "{editIngredienteDto.nombre.size}")
        String nombre
) {

    public Ingrediente to() {
        return Ingrediente.builder()
                .nombre(nombre)
                .build();
    }
}
