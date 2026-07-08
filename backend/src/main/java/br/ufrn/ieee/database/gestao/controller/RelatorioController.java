package br.ufrn.ieee.database.gestao.controller;

import br.ufrn.ieee.database.gestao.dto.RelatorioRequestDTO;
import br.ufrn.ieee.database.gestao.dto.RelatorioResponseDTO;
import br.ufrn.ieee.database.gestao.service.RelatorioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping
    public ResponseEntity<List<RelatorioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(relatorioService.listarTodos());
    }

    @PreAuthorize("hasAnyRole('DIRETOR_RAMO', 'DIRETOR_CAPITULO')")
    @PostMapping
    public ResponseEntity<RelatorioResponseDTO> criar(@RequestBody RelatorioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(relatorioService.criar(dto));
    }
}