package br.ufrn.ieee.database.voluntario.dto;

import br.ufrn.ieee.database.gestao.dto.MandatoResponseDTO;
import lombok.Data;
import java.util.List;

@Data
public class VoluntarioPerfilResponseDTO {
    private Long id;
    private String primeiroNome;
    private String ultimoNome;
    private String emailPessoal;
    private String tipoUsuario;

    private String numeroMembresia;
    private String emailIeee;
    private String tipoMembresia;

    private List<MandatoResponseDTO> historicoMandatos;
}