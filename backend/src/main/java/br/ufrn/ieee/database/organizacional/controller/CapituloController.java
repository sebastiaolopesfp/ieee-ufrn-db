package br.ufrn.ieee.database.organizacional.controller;

import br.ufrn.ieee.database.organizacional.dto.CapituloRequestDTO;
import br.ufrn.ieee.database.organizacional.dto.CapituloResponseDTO;
import br.ufrn.ieee.database.organizacional.service.CapituloService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/capitulos")
public class CapituloController {

    private final CapituloService capituloService;

    public CapituloController(CapituloService capituloService) {
        this.capituloService = capituloService;
    }

    @GetMapping
    public ResponseEntity<Page<CapituloResponseDTO>> listar(
            @PageableDefault(size = 20, sort = "unidadeCodigo") Pageable pageable) {
        return ResponseEntity.ok(capituloService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CapituloResponseDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(capituloService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CapituloResponseDTO> criar(@Valid @RequestBody CapituloRequestDTO dto) {
        CapituloResponseDTO capituloCriado = capituloService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(capituloCriado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    // Sem @Valid: atualização parcial (Service só seta campos != null).
    public ResponseEntity<CapituloResponseDTO> atualizar(@PathVariable String id, @RequestBody CapituloRequestDTO dto) {
        return ResponseEntity.ok(capituloService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        capituloService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}