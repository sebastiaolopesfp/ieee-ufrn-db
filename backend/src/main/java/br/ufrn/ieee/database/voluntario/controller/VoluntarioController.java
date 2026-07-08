package br.ufrn.ieee.database.voluntario.controller;

import br.ufrn.ieee.database.voluntario.dto.AdminUpdateEmailCPFRequestDTO;
import br.ufrn.ieee.database.voluntario.dto.AlterarSenhaRequestDTO;
import br.ufrn.ieee.database.voluntario.dto.AtualizarCargoRequestDTO;
import br.ufrn.ieee.database.voluntario.dto.PromoverDiretorRequestDTO;
import br.ufrn.ieee.database.voluntario.dto.PromoverMembroRequestDTO;
import br.ufrn.ieee.database.voluntario.dto.VoluntarioRequestDTO;
import br.ufrn.ieee.database.voluntario.dto.VoluntarioResponseDTO;
import br.ufrn.ieee.database.voluntario.dto.VoluntarioPerfilResponseDTO;
import br.ufrn.ieee.database.voluntario.service.VoluntarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/voluntarios")
public class VoluntarioController {

    private final VoluntarioService voluntarioService;

    public VoluntarioController(VoluntarioService voluntarioService) {
        this.voluntarioService = voluntarioService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<List<VoluntarioResponseDTO>> listar() {
        return ResponseEntity.ok(voluntarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoluntarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(voluntarioService.buscarPorId(id));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<VoluntarioResponseDTO> cadastrar(@RequestBody VoluntarioRequestDTO dto) {
        VoluntarioResponseDTO response = voluntarioService.cadastrarVoluntario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @voluntarioSecurity.isOwner(#id, authentication)")
    public ResponseEntity<VoluntarioResponseDTO> atualizar(@PathVariable Long id,
            @RequestBody VoluntarioRequestDTO dto) {
        return ResponseEntity.ok(voluntarioService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        voluntarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/perfil")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO') or @voluntarioSecurity.isOwner(#id, authentication)")
    public ResponseEntity<VoluntarioPerfilResponseDTO> obterPerfil(@PathVariable Long id) {
        return ResponseEntity.ok(voluntarioService.obterPerfilCompleto(id));
    }

    @PostMapping("/{id}/promover-membro")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<Void> promoverMembro(@PathVariable Long id, @RequestBody PromoverMembroRequestDTO dto) {
        voluntarioService.promoverAMembro(id, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/promover-diretor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> promoverDiretor(@PathVariable Long id, @RequestBody PromoverDiretorRequestDTO dto) {
        voluntarioService.promoverADiretor(id, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/alterar-cargo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> alterarCargoDiretor(@PathVariable Long id, @RequestBody AtualizarCargoRequestDTO dto) {
        voluntarioService.alterarCargoDiretor(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/remover-membro")
    @PreAuthorize("hasAnyRole('ADMIN','DIRETOR_RAMO','DIRETOR_CAPITULO')")
    public ResponseEntity<Void> removerMembresia(@PathVariable Long id) {
        voluntarioService.removerMembresia(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/remover-diretor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removerDiretoria(@PathVariable Long id) {
        voluntarioService.removerDiretoria(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/alterar-senha")
    @PreAuthorize("hasRole('ADMIN') or @voluntarioSecurity.isOwner(#id, authentication)")
    public ResponseEntity<Void> alterarSenha(@PathVariable Long id, @RequestBody AlterarSenhaRequestDTO dto) {
        voluntarioService.alterarSenha(id, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/admin/{id}/identidade")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> adminAtualizarEmailCPF(@PathVariable Long id,
            @RequestBody AdminUpdateEmailCPFRequestDTO dto) {
        voluntarioService.adminAtualizarEmailCPF(id, dto);
        return ResponseEntity.ok().build();
    }
}