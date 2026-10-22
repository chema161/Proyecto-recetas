package org.salesianostriana.dam.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.salesianostriana.dam.Dto.EditIngredienteDto;
import org.salesianostriana.dam.Dto.GetIngredienteDto;
import org.salesianostriana.dam.Service.IngredienteService;
import org.salesianostriana.dam.modelos.Ingrediente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ingredientes")
public class IngredienteController {

    private final IngredienteService service;

    @GetMapping
    public ResponseEntity<List<GetIngredienteDto>> getAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(GetIngredienteDto::of)
                        .toList()
        );
    }

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
