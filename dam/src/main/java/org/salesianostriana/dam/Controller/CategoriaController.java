package org.salesianostriana.dam.Controller;

import org.salesianostriana.dam.Dto.EditCategoriaDto;
import org.salesianostriana.dam.Dto.GetCategoriaDto;
import org.salesianostriana.dam.Service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.salesianostriana.dam.modelos.Categoria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService service;

    @GetMapping
    public ResponseEntity<List<GetCategoriaDto>> getAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(GetCategoriaDto::of)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCategoriaDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(GetCategoriaDto.of(service.findById(id)));
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<GetCategoriaDto> edit(@PathVariable Long id,
                                                @Valid @RequestBody EditCategoriaDto dto) {
        return ResponseEntity.ok(GetCategoriaDto.of(service.edit(id, dto.to())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
