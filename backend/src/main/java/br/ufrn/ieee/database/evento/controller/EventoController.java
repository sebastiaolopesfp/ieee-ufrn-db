package br.ufrn.ieee.database.evento.controller;

import br.ufrn.ieee.database.evento.dto.EventoRequestDTO;
import br.ufrn.ieee.database.evento.dto.EventoResponseDTO;
import br.ufrn.ieee.database.evento.service.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public ResponseEntity<EventoResponseDTO> criarLocalmente(@RequestBody EventoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.criarLocalmente(dto));
    }

    @PostMapping("/vtools")
    public ResponseEntity<EventoResponseDTO> importarOuAtualizarDoVTools(
            @RequestParam String vtoolsId,
            @RequestParam(required = false) String unidadeCodigo) {
        return ResponseEntity.ok(eventoService.importarOuAtualizarDoVTools(vtoolsId, unidadeCodigo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}