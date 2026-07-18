package br.ufrn.ieee.database.academico.controller;

import br.ufrn.ieee.database.academico.dto.VinculoRequestDTO;
import br.ufrn.ieee.database.academico.dto.VinculoResponseDTO;
import br.ufrn.ieee.database.academico.service.VinculoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vinculos")
public class VinculoController {

    private final VinculoService vinculoService;

    public VinculoController(VinculoService vinculoService) {
        this.vinculoService = vinculoService;
    }

    @GetMapping
    public ResponseEntity<Page<VinculoResponseDTO>> listar(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(vinculoService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VinculoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vinculoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<VinculoResponseDTO> criar(@Valid @RequestBody VinculoRequestDTO dto) {
        VinculoResponseDTO vinculoCriado = vinculoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(vinculoCriado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<VinculoResponseDTO> atualizar(@PathVariable Long id,
            @Valid @RequestBody VinculoRequestDTO dto) {
        return ResponseEntity.ok(vinculoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        vinculoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}