package br.ufrn.ieee.database.gestao.controller;

import br.ufrn.ieee.database.gestao.dto.CargoRequestDTO;
import br.ufrn.ieee.database.gestao.dto.CargoResponseDTO;
import br.ufrn.ieee.database.gestao.service.CargoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cargos")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping
    public ResponseEntity<List<CargoResponseDTO>> listar() {
        return ResponseEntity.ok(cargoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cargoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CargoResponseDTO> criar(@RequestBody CargoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cargoService.criar(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CargoResponseDTO> atualizar(@PathVariable Long id, @RequestBody CargoRequestDTO dto) {
        return ResponseEntity.ok(cargoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cargoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}