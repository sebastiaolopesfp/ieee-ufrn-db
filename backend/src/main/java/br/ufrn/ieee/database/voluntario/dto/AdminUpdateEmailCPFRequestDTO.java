package br.ufrn.ieee.database.voluntario.dto;

import lombok.Data;

@Data
public class AdminUpdateEmailCPFRequestDTO {
    private String novoEmail;
    private String novoCpf;
}