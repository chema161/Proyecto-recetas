package org.salesianostriana.dam.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.salesianostriana.dam.Validation.NombreRecetaUnico;
import org.salesianostriana.dam.modelos.Categoria;
import org.salesianostriana.dam.modelos.Dificultad;
import org.salesianostriana.dam.modelos.Receta;

@Schema(description = "Datos para crear o editar una receta")
public record EditRecetaDto(
        @Schema(description = "Nombre único de la receta", example = "Paella valenciana")
        @NotBlank(message = "{editRecetaDto.nombre.notBlank}")
        @Size(max = 150, message = "{editRecetaDto.nombre.size}")
        @NombreRecetaUnico
        String nombre,

        @Schema(description = "Tiempo de preparación en minutos (debe ser mayor que 0)", example = "90")
        @NotNull(message = "{editRecetaDto.tiempo.notNull}")
        Integer tiempoPreparacionMin,

        @Schema(description = "Nivel de dificultad", example = "MEDIA")
        @NotNull(message = "{editRecetaDto.dificultad.notNull}")
        Dificultad dificultad,

        @Schema(description = "Id de una categoría existente", example = "1")
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