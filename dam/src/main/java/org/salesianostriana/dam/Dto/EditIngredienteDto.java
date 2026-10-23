package org.salesianostriana.dam.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.salesianostriana.dam.modelos.Ingrediente;

@Schema(description = "Datos para crear un ingrediente")
public record EditIngredienteDto(
        @Schema(description = "Nombre único del ingrediente", example = "Arroz bomba")
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
