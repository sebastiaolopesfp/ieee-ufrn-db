package br.ufrn.ieee.database.evento.dto.vtools;

import lombok.Data;

@Data
public class VToolsEventDataDTO {
    private String type;
    private String id;
    private VToolsEventAttributesDTO attributes;
    private VToolsRelationshipsDTO relationships;
}