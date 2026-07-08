package br.ufrn.ieee.database.evento.controller;

import br.ufrn.ieee.database.evento.dto.EventoRequestDTO;
import br.ufrn.ieee.database.evento.dto.EventoResponseDTO;
import br.ufrn.ieee.database.evento.service.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> listar() {
        return ResponseEntity.ok(eventoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.buscarPorId(id));
    }

    @PostMapping("/local")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<EventoResponseDTO> criarLocalmente(@RequestBody EventoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.criarLocalmente(dto));
    }

    @PostMapping("/vtools")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<EventoResponseDTO> importarOuAtualizarDoVTools(
            @RequestParam String vtoolsId,
            @RequestParam(required = false) String unidadeCodigo) {
        return ResponseEntity.ok(eventoService.importarOuAtualizarDoVTools(vtoolsId, unidadeCodigo));
    }

    @PutMapping("/local/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<EventoResponseDTO> atualizarLocalmente(
            @PathVariable Long id,
            @RequestBody EventoRequestDTO dto) {
        return ResponseEntity.ok(eventoService.atualizarLocalmente(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categorias")
    public ResponseEntity<Map<String, String>> listarCategorias() {
        return ResponseEntity.ok(eventoService.listarCategorias());
    }

    @GetMapping("/subcategorias")
    public ResponseEntity<Map<String, String>> listarSubcategorias() {
        return ResponseEntity.ok(eventoService.listarSubcategorias());
    }
}