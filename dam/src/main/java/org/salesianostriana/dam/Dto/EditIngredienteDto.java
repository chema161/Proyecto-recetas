package org.salesianostriana.dam.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.salesianostriana.dam.modelos.Ingrediente;

public record EditIngredienteDto(
        @NotBlank(message = "El nombre del ingrediente es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre
) {

    public Ingrediente to() {
        return Ingrediente.builder()
                .nombre(nombre)
                .build();
    }
}
