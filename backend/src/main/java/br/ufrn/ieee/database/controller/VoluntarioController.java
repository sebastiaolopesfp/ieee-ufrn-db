package br.ufrn.ieee.database.controller;

import br.ufrn.ieee.database.dto.VoluntarioRequestDTO;
import br.ufrn.ieee.database.dto.VoluntarioResponseDTO;
import br.ufrn.ieee.database.service.VoluntarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voluntarios")
public class VoluntarioController {

    private final VoluntarioService voluntarioService;

    public VoluntarioController(VoluntarioService voluntarioService) {
        this.voluntarioService = voluntarioService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<VoluntarioResponseDTO> cadastrar(@RequestBody VoluntarioRequestDTO dto) {
        VoluntarioResponseDTO response = voluntarioService.cadastrarVoluntario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}