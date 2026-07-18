package br.ufrn.ieee.database.financeiro.controller;

import br.ufrn.ieee.database.financeiro.dto.ItemOrcamentoRequestDTO;
import br.ufrn.ieee.database.financeiro.dto.ItemOrcamentoResponseDTO;
import br.ufrn.ieee.database.financeiro.service.ItemOrcamentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itens-orcamento")
public class ItemOrcamentoController {

    private final ItemOrcamentoService itemOrcamentoService;

    public ItemOrcamentoController(ItemOrcamentoService itemOrcamentoService) {
        this.itemOrcamentoService = itemOrcamentoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<Page<ItemOrcamentoResponseDTO>> listar(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(itemOrcamentoService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<ItemOrcamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(itemOrcamentoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<ItemOrcamentoResponseDTO> criar(@Valid @RequestBody ItemOrcamentoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemOrcamentoService.criar(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    // Sem @Valid: atualização parcial (cada campo só é setado se != null).
    public ResponseEntity<ItemOrcamentoResponseDTO> atualizar(@PathVariable Long id,
            @RequestBody ItemOrcamentoRequestDTO dto) {
        return ResponseEntity.ok(itemOrcamentoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        itemOrcamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}