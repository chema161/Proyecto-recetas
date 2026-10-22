package org.salesianostriana.dam.Dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.salesianostriana.dam.modelos.Categoria;
import org.salesianostriana.dam.modelos.Dificultad;
import org.salesianostriana.dam.modelos.Receta;

public record EditRecetaDto(
        @NotBlank(message = "El nombre de la receta es obligatorio")
        @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
        String nombre,

        @NotNull(message = "El tiempo de preparación es obligatorio")
        Integer tiempoPreparacionMin,

        @NotNull(message = "La dificultad es obligatoria (FACIL, MEDIA o DIFICIL)")
        Dificultad dificultad,

        @NotNull(message = "La categoría es obligatoria")
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