package br.ufrn.ieee.database.academico.dto;

import lombok.Data;

import java.util.List;

@Data
public class InstituicaoRequestDTO {
    private String nome;
    private String sigla;
    private List<Long> cursoIds;
}