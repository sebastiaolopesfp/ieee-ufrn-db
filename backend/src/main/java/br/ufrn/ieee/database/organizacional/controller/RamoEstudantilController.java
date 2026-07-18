package br.ufrn.ieee.database.organizacional.controller;

import br.ufrn.ieee.database.organizacional.dto.RamoEstudantilRequestDTO;
import br.ufrn.ieee.database.organizacional.dto.RamoEstudantilResponseDTO;
import br.ufrn.ieee.database.organizacional.service.RamoEstudantilService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ramos-estudantis")
public class RamoEstudantilController {

    private final RamoEstudantilService ramoService;

    public RamoEstudantilController(RamoEstudantilService ramoService) {
        this.ramoService = ramoService;
    }

    @GetMapping
    public ResponseEntity<Page<RamoEstudantilResponseDTO>> listar(
            @PageableDefault(size = 20, sort = "unidadeCodigo") Pageable pageable) {
        return ResponseEntity.ok(ramoService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RamoEstudantilResponseDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(ramoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RamoEstudantilResponseDTO> criar(@Valid @RequestBody RamoEstudantilRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ramoService.criar(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    // Sem @Valid: atualização parcial (Service só seta campos != null).
    public ResponseEntity<RamoEstudantilResponseDTO> atualizar(@PathVariable String id,
            @RequestBody RamoEstudantilRequestDTO dto) {
        return ResponseEntity.ok(ramoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        ramoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}