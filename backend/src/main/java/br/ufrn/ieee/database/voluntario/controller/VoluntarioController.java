package br.ufrn.ieee.database.voluntario.controller;

import br.ufrn.ieee.database.voluntario.dto.PromoverDiretorRequestDTO;
import br.ufrn.ieee.database.voluntario.dto.PromoverMembroRequestDTO;
import br.ufrn.ieee.database.voluntario.dto.VoluntarioRequestDTO;
import br.ufrn.ieee.database.voluntario.dto.VoluntarioResponseDTO;
import br.ufrn.ieee.database.voluntario.service.VoluntarioService;

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

    @PostMapping("/{id}/promover-membro")
    public ResponseEntity<Void> promoverMembro(@PathVariable Long id, @RequestBody PromoverMembroRequestDTO dto) {
        voluntarioService.promoverAMembro(id, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/promover-diretor")
    public ResponseEntity<Void> promoverDiretor(@PathVariable Long id, @RequestBody PromoverDiretorRequestDTO dto) {
        voluntarioService.promoverADiretor(id, dto);
        return ResponseEntity.ok().build();
    }
}