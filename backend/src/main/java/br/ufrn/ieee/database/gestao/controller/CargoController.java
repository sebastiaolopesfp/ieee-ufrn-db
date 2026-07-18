package br.ufrn.ieee.database.gestao.controller;

import br.ufrn.ieee.database.gestao.dto.CargoRequestDTO;
import br.ufrn.ieee.database.gestao.dto.CargoResponseDTO;
import br.ufrn.ieee.database.gestao.service.CargoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cargos")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping
    public ResponseEntity<Page<CargoResponseDTO>> listar(
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(cargoService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cargoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CargoResponseDTO> criar(@Valid @RequestBody CargoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cargoService.criar(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    // Sem @Valid: atualização parcial (Service só seta nome se != null).
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