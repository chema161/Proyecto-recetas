package org.salesianostriana.dam.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.salesianostriana.dam.Dto.EditIngredienteDto;
import org.salesianostriana.dam.Dto.GetIngredienteDto;
import org.salesianostriana.dam.Service.IngredienteService;
import org.salesianostriana.dam.modelos.Ingrediente;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ingredientes")
@Tag(name = "Ingredientes", description = "Alta y listado de ingredientes")
public class IngredienteController {

    private final IngredienteService service;

    @Operation(summary = "Listar ingredientes")
    @ApiResponse(responseCode = "200", description = "Listado de ingredientes")
    @GetMapping
    public ResponseEntity<List<GetIngredienteDto>> getAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(GetIngredienteDto::of)
                        .toList()
        );
    }

    @Operation(summary = "Crear un ingrediente",
                description = "El nombre es obligatorio y debe ser único")
    @ApiResponses({
         @ApiResponse(responseCode = "201", description = "Ingrediente creado"),
         @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos",
                 content = @Content(mediaType = "application/problem+json",
                         schema = @Schema(implementation = ProblemDetail.class))),
         @ApiResponse(responseCode = "409", description = "Ya existe un ingrediente con ese nombre",
                 content = @Content(mediaType = "application/problem+json",
                         schema = @Schema(implementation = ProblemDetail.class))),
    })
    @PostMapping
    public ResponseEntity<GetIngredienteDto> create(@Valid @RequestBody EditIngredienteDto dto) {
        Ingrediente creado = service.save(dto.to());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();

        return ResponseEntity.created(location).body(GetIngredienteDto.of(creado));
    }
}
