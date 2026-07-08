package br.ufrn.ieee.database.academico.controller;

import br.ufrn.ieee.database.academico.dto.InstituicaoRequestDTO;
import br.ufrn.ieee.database.academico.dto.InstituicaoResponseDTO;
import br.ufrn.ieee.database.academico.service.InstituicaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instituicoes")
public class InstituicaoController {

    private final InstituicaoService instituicaoService;

    public InstituicaoController(InstituicaoService instituicaoService) {
        this.instituicaoService = instituicaoService;
    }

    @GetMapping
    public ResponseEntity<List<InstituicaoResponseDTO>> listar() {
        return ResponseEntity.ok(instituicaoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(instituicaoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstituicaoResponseDTO> criar(@RequestBody InstituicaoRequestDTO dto) {
        InstituicaoResponseDTO instituicaoCriada = instituicaoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(instituicaoCriada);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstituicaoResponseDTO> atualizar(@PathVariable Long id,
            @RequestBody InstituicaoRequestDTO dto) {
        return ResponseEntity.ok(instituicaoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        instituicaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}