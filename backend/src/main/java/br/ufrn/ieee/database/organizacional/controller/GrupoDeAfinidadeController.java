package br.ufrn.ieee.database.organizacional.controller;

import br.ufrn.ieee.database.organizacional.dto.GrupoDeAfinidadeRequestDTO;
import br.ufrn.ieee.database.organizacional.dto.GrupoDeAfinidadeResponseDTO;
import br.ufrn.ieee.database.organizacional.service.GrupoDeAfinidadeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos-afinidade")
public class GrupoDeAfinidadeController {

    private final GrupoDeAfinidadeService grupoService;

    public GrupoDeAfinidadeController(GrupoDeAfinidadeService grupoService) {
        this.grupoService = grupoService;
    }

    @GetMapping
    public ResponseEntity<List<GrupoDeAfinidadeResponseDTO>> listar() {
        return ResponseEntity.ok(grupoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoDeAfinidadeResponseDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(grupoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<GrupoDeAfinidadeResponseDTO> criar(@RequestBody GrupoDeAfinidadeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(grupoService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoDeAfinidadeResponseDTO> atualizar(@PathVariable String id, @RequestBody GrupoDeAfinidadeRequestDTO dto) {
        return ResponseEntity.ok(grupoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        grupoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}