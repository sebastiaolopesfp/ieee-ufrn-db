package br.ufrn.ieee.database.academico.controller;

import br.ufrn.ieee.database.academico.dto.VinculoRequestDTO;
import br.ufrn.ieee.database.academico.dto.VinculoResponseDTO;
import br.ufrn.ieee.database.academico.service.VinculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vinculos")
public class VinculoController {

    private final VinculoService vinculoService;

    public VinculoController(VinculoService vinculoService) {
        this.vinculoService = vinculoService;
    }

    @GetMapping
    public ResponseEntity<List<VinculoResponseDTO>> listar() {
        return ResponseEntity.ok(vinculoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VinculoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vinculoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<VinculoResponseDTO> criar(@RequestBody VinculoRequestDTO dto) {
        VinculoResponseDTO vinculoCriado = vinculoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(vinculoCriado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<VinculoResponseDTO> atualizar(@PathVariable Long id, @RequestBody VinculoRequestDTO dto) {
        return ResponseEntity.ok(vinculoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        vinculoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}