package org.salesianostriana.dam.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.salesianostriana.dam.Dto.AddIngredienteRecetaDto;
import org.salesianostriana.dam.Dto.EditRecetaDto;
import org.salesianostriana.dam.Dto.GetRecetaDetailDto;
import org.salesianostriana.dam.Dto.GetRecetaListDto;
import org.salesianostriana.dam.Service.RecetaService;
import org.salesianostriana.dam.modelos.Receta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recetas")
@Tag(name = "Recetas", description = "CRUD de recetas y gestión de sus ingredientes")
public class RecetaController {

    private final RecetaService service;

    @Operation(summary = "Listar recetas",
            description = "Devuelve un resumen de cada receta con el nombre de su categoría.")
    @ApiResponse(responseCode = "200", description = "Listado de recetas ")
    @GetMapping
    public ResponseEntity<List<GetRecetaListDto>> getAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(GetRecetaListDto::of)
                        .toList()
        );
    }

    @Operation(summary = "Obtener una receta con sus ingredientes",
            description = "Devuelve el detalle de la receta con su categoría y la lista de ingredientes anidados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receta encontrada"),
            @ApiResponse(responseCode = "404", description = "La receta no existe",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })

    @GetMapping("/{id}")
    public ResponseEntity<GetRecetaDetailDto> getById(
            @Parameter(description = "Id de la receta", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(GetRecetaDetailDto.of(service.findById(id)));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Receta creada"),
            @ApiResponse(responseCode = "400",
                    description = "Datos inválidos, nombre duplicado o tiempo de preparación menor o igual a 0",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "La categoría indicada no existe",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })

    @PostMapping
    public ResponseEntity<GetRecetaDetailDto> create(@Valid @RequestBody EditRecetaDto dto) {
        Receta creada = service.save(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.getId())
                .toUri();

        return ResponseEntity.created(location).body(GetRecetaDetailDto.of(creada));
    }

    @Operation(summary = "Editar una receta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receta actualizada"),
            @ApiResponse(responseCode = "400",
                    description = "Datos inválidos, nombre duplicado o tiempo de preparación menor o igual a 0",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "La receta o la categoría indicada no existen",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })

    @PutMapping("/{id}")
    public ResponseEntity<GetRecetaDetailDto> edit(
            @Parameter(description = "Id de la receta", example = "1") @PathVariable Long id,
            @Valid @RequestBody EditRecetaDto dto) {
        return ResponseEntity.ok(GetRecetaDetailDto.of(service.edit(id, dto)));
    }

    @Operation(summary = "Borrar una receta",
            description = "Es idempotente: si no existe, responde 204 igualmente.")
    @ApiResponse(responseCode = "204", description = "Receta borrada (o no existía)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id de la receta", example = "1") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Añadir un ingrediente a una receta",
            description = "Relación Many to Many. Asocia un ingrediente existente a una receta existente. "
                    + "Devuelve la receta con la lista de ingredientes actualizada.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ingrediente añadido a la receta"),
            @ApiResponse(responseCode = "400", description = "Falta el id del ingrediente o no es válido",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "La receta o el ingrediente no existen",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "La receta ya contiene ese ingrediente",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })

    @PostMapping("/{recetaId}/ingredientes")
    public ResponseEntity<GetRecetaDetailDto> addIngrediente(
            @Parameter(description = "Id de la receta", example = "1") @PathVariable Long recetaId,
            @Valid @RequestBody AddIngredienteRecetaDto dto) {

        Receta receta = service.addIngrediente(recetaId, dto.ingredienteId());
        return ResponseEntity.status(HttpStatus.CREATED).body(GetRecetaDetailDto.of(receta));
    }
}


