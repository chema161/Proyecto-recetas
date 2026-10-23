package org.salesianostriana.dam.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.salesianostriana.dam.Dto.EditCategoriaDto;
import org.salesianostriana.dam.Dto.GetCategoriaDto;
import org.salesianostriana.dam.Service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.salesianostriana.dam.modelos.Categoria;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categorias")
@Tag(name = "Categorías", description = "CRUD de categorías de recetas")
public class CategoriaController {

    private final CategoriaService service;

    @Operation(summary = "Listar categorías", description = "Devuelve todas las categorías.")
    @ApiResponse(responseCode = "200", description = "Listado de categorías")
    @GetMapping
    public ResponseEntity<List<GetCategoriaDto>> getAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(GetCategoriaDto::of)
                        .toList()
        );
    }

    @Operation(summary = "Obtener una categoría por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "La categoría no existe",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })

    @GetMapping("/{id}")
    public ResponseEntity<GetCategoriaDto> getById(
            @Parameter(description = "Id de la categoría", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(GetCategoriaDto.of(service.findById(id)));
    }

    @Operation(summary = "Crear una categoría",
            description = "El nombre es obligatorio y debe ser único.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoría creada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Ya existe una categoría con ese nombre",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })

    @PostMapping
    public ResponseEntity<GetCategoriaDto> create(@Valid @RequestBody EditCategoriaDto dto) {
        Categoria creada = service.save(dto.to());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.getId())
                .toUri();

        return ResponseEntity.created(location).body(GetCategoriaDto.of(creada));
    }

    @Operation(summary = "Editar una categoría")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría actualizada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "La categoría no existe",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Ya existe otra categoría con ese nombre",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })

    @PutMapping("/{id}")
    public ResponseEntity<GetCategoriaDto> edit(
            @Parameter(description = "Id de la categoría", example = "1") @PathVariable Long id,
            @Valid @RequestBody EditCategoriaDto dto) {
        return ResponseEntity.ok(GetCategoriaDto.of(service.edit(id, dto.to())));
    }
        @Operation(summary = "Borrar una categoría",
                description = "Es idempotente: si no existe, responde 204 igualmente. "
                        + "No se puede borrar una categoría que todavía tiene recetas.")
        @ApiResponses({
                @ApiResponse(responseCode = "204", description = "Categoría borrada (o no existía)"),
                @ApiResponse(responseCode = "409", description = "La categoría tiene recetas asociadas",
                        content = @Content(mediaType = "application/problem+json",
                                schema = @Schema(implementation = ProblemDetail.class)))
        })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id de la categoría", example = "1") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
