package org.salesianostriana.dam.Dto;

import org.salesianostriana.dam.modelos.Dificultad;
import org.salesianostriana.dam.modelos.Receta;

public record GetRecetaListDto(
        Long id,
        String nombre,
        Integer tiempoPreparacionMin,
        Dificultad dificultad,
        String categoria
) {

    public static GetRecetaListDto of(Receta receta) {
        return new GetRecetaListDto(
                receta.getId(),
                receta.getNombre(),
                receta.getTiempoPreparacionMin(),
                receta.getDificultad(),
                receta.getCategoria().getNombre()
        );
    }
}
