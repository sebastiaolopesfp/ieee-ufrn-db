package br.ufrn.ieee.database.financeiro.controller;

import br.ufrn.ieee.database.financeiro.dto.FornecedorRequestDTO;
import br.ufrn.ieee.database.financeiro.dto.FornecedorResponseDTO;
import br.ufrn.ieee.database.financeiro.service.FornecedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<List<FornecedorResponseDTO>> listar() {
        return ResponseEntity.ok(fornecedorService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<FornecedorResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<FornecedorResponseDTO> criar(@RequestBody FornecedorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorService.criar(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<FornecedorResponseDTO> atualizar(@PathVariable Long id,
            @RequestBody FornecedorRequestDTO dto) {
        return ResponseEntity.ok(fornecedorService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        fornecedorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}