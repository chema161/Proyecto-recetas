package org.salesianostriana.dam.Dto;

import org.salesianostriana.dam.modelos.Dificultad;
import org.salesianostriana.dam.modelos.Receta;

import java.util.List;

public record GetRecetaDetailDto(
        Long id,
        String nombre,
        Integer tiempoPreparacionMin,
        Dificultad dificultad,
        GetCategoriaDto categoria,
        List<GetIngredienteDto> ingredientes
) {

    public static GetRecetaDetailDto of(Receta receta) {
        return new GetRecetaDetailDto(
                receta.getId(),
                receta.getNombre(),
                receta.getTiempoPreparacionMin(),
                receta.getDificultad(),
                GetCategoriaDto.of(receta.getCategoria()),
                receta.getIngredientes().stream()
                        .map(GetIngredienteDto::of)
                        .toList()
        );
    }
}
