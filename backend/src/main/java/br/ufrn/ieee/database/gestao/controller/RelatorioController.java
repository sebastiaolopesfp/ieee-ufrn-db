package br.ufrn.ieee.database.gestao.controller;

import br.ufrn.ieee.database.gestao.dto.RelatorioRequestDTO;
import br.ufrn.ieee.database.gestao.dto.RelatorioResponseDTO;
import br.ufrn.ieee.database.gestao.service.RelatorioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping
    public ResponseEntity<Page<RelatorioResponseDTO>> listarTodos(
            @PageableDefault(size = 20, sort = "dataGeracao") Pageable pageable) {
        return ResponseEntity.ok(relatorioService.listarTodos(pageable));
    }

    @PreAuthorize("hasAnyRole('DIRETOR_RAMO', 'DIRETOR_CAPITULO')")
    @PostMapping
    public ResponseEntity<RelatorioResponseDTO> criar(@Valid @RequestBody RelatorioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(relatorioService.criar(dto));
    }
}