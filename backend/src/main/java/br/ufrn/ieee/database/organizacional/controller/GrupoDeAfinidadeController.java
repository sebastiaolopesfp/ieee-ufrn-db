package br.ufrn.ieee.database.organizacional.controller;

import br.ufrn.ieee.database.organizacional.dto.GrupoDeAfinidadeRequestDTO;
import br.ufrn.ieee.database.organizacional.dto.GrupoDeAfinidadeResponseDTO;
import br.ufrn.ieee.database.organizacional.service.GrupoDeAfinidadeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grupos-afinidade")
public class GrupoDeAfinidadeController {

    private final GrupoDeAfinidadeService grupoService;

    public GrupoDeAfinidadeController(GrupoDeAfinidadeService grupoService) {
        this.grupoService = grupoService;
    }

    @GetMapping
    public ResponseEntity<Page<GrupoDeAfinidadeResponseDTO>> listar(
            @PageableDefault(size = 20, sort = "unidadeCodigo") Pageable pageable) {
        return ResponseEntity.ok(grupoService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoDeAfinidadeResponseDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(grupoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GrupoDeAfinidadeResponseDTO> criar(@Valid @RequestBody GrupoDeAfinidadeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(grupoService.criar(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    // Sem @Valid: atualização parcial (Service só seta campos != null).
    public ResponseEntity<GrupoDeAfinidadeResponseDTO> atualizar(@PathVariable String id,
            @RequestBody GrupoDeAfinidadeRequestDTO dto) {
        return ResponseEntity.ok(grupoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        grupoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}