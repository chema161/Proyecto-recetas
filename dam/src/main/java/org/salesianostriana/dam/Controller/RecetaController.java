package org.salesianostriana.dam.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.salesianostriana.dam.Dto.AddIngredienteRecetaDto;
import org.salesianostriana.dam.Dto.EditRecetaDto;
import org.salesianostriana.dam.Dto.GetRecetaDetailDto;
import org.salesianostriana.dam.Dto.GetRecetaListDto;
import org.salesianostriana.dam.Service.RecetaService;
import org.salesianostriana.dam.modelos.Receta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recetas")
public class RecetaController {

    private final RecetaService service;

    @GetMapping
    public ResponseEntity<List<GetRecetaListDto>> getAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(GetRecetaListDto::of)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetRecetaDetailDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(GetRecetaDetailDto.of(service.findById(id)));
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<GetRecetaDetailDto> edit(@PathVariable Long id,
                                                   @Valid @RequestBody EditRecetaDto dto) {
        return ResponseEntity.ok(GetRecetaDetailDto.of(service.edit(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{recetaId}/ingredientes")
    public ResponseEntity<GetRecetaDetailDto> addIngrediente(
            @PathVariable Long recetaId,
            @Valid @RequestBody AddIngredienteRecetaDto dto) {

        Receta receta = service.addIngrediente(recetaId, dto.ingredienteId());
        return ResponseEntity.status(HttpStatus.CREATED).body(GetRecetaDetailDto.of(receta));
    }
}


