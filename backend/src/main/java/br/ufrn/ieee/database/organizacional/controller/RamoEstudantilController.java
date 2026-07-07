package br.ufrn.ieee.database.organizacional.controller;

import br.ufrn.ieee.database.organizacional.dto.RamoEstudantilRequestDTO;
import br.ufrn.ieee.database.organizacional.dto.RamoEstudantilResponseDTO;
import br.ufrn.ieee.database.organizacional.service.RamoEstudantilService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ramos-estudantis")
public class RamoEstudantilController {

    private final RamoEstudantilService ramoService;

    public RamoEstudantilController(RamoEstudantilService ramoService) {
        this.ramoService = ramoService;
    }

    @GetMapping
    public ResponseEntity<List<RamoEstudantilResponseDTO>> listar() {
        return ResponseEntity.ok(ramoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RamoEstudantilResponseDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(ramoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<RamoEstudantilResponseDTO> criar(@RequestBody RamoEstudantilRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ramoService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RamoEstudantilResponseDTO> atualizar(@PathVariable String id, @RequestBody RamoEstudantilRequestDTO dto) {
        return ResponseEntity.ok(ramoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        ramoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}