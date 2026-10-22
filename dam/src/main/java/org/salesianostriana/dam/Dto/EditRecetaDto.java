package org.salesianostriana.dam.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.salesianostriana.dam.Validation.NombreRecetaUnico;
import org.salesianostriana.dam.modelos.Categoria;
import org.salesianostriana.dam.modelos.Dificultad;
import org.salesianostriana.dam.modelos.Receta;

public record EditRecetaDto(
        @NotBlank(message = "{editRecetaDto.nombre.notBlank}")
        @Size(max = 150, message = "{editRecetaDto.nombre.size}")
        @NombreRecetaUnico
        String nombre,

        @NotNull(message = "{editRecetaDto.tiempo.notNull}")
        Integer tiempoPreparacionMin,

        @NotNull(message = "{editRecetaDto.dificultad.notNull}")
        Dificultad dificultad,

        @NotNull(message = "{editRecetaDto.categoriaId.notNull}")
        @Positive(message = "{editRecetaDto.categoriaId.positive}")
        Long categoriaId
) {

    public Receta to(Categoria categoria) {
        return Receta.builder()
                .nombre(nombre)
                .tiempoPreparacionMin(tiempoPreparacionMin)
                .dificultad(dificultad)
                .categoria(categoria)
                .build();
    }
}