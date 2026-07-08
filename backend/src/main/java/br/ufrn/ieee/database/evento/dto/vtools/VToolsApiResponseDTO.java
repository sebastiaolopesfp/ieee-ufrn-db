package br.ufrn.ieee.database.evento.dto.vtools;

import lombok.Data;
import java.util.List;

@Data
public class VToolsApiResponseDTO {
    private List<VToolsEventDataDTO> data;
}