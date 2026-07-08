package br.ufrn.ieee.database.evento.controller;

import br.ufrn.ieee.database.evento.dto.SessaoRequestDTO;
import br.ufrn.ieee.database.evento.dto.SessaoResponseDTO;
import br.ufrn.ieee.database.evento.service.SessaoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/sessoes")
public class SessaoController {

    private final SessaoService sessaoService;

    public record RegistrarPresencaRequestDTO(@NotEmpty Set<Long> voluntarioIds) {
    }

    public SessaoController(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<SessaoResponseDTO>> listarPorEvento(@PathVariable Long eventoId) {
        return ResponseEntity.ok(sessaoService.listarPorEvento(eventoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(sessaoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<SessaoResponseDTO> criar(@RequestBody SessaoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sessaoService.criar(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<SessaoResponseDTO> atualizar(@PathVariable Long id, @RequestBody SessaoRequestDTO dto) {
        return ResponseEntity.ok(sessaoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        sessaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/presenca")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<SessaoResponseDTO> registrarPresenca(@PathVariable Long id,
            @Valid @RequestBody RegistrarPresencaRequestDTO dto) {
        return ResponseEntity.ok(sessaoService.registrarPresenca(id, dto.voluntarioIds()));
    }

    @DeleteMapping("/{id}/presenca/{voluntarioId}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<SessaoResponseDTO> removerPresenca(
            @PathVariable Long id,
            @PathVariable Long voluntarioId) {
        return ResponseEntity.ok(sessaoService.removerPresenca(id, voluntarioId));
    }
}