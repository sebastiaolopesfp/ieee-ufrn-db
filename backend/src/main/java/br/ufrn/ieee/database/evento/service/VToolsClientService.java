package br.ufrn.ieee.database.evento.service;

import br.ufrn.ieee.database.evento.dto.vtools.VToolsApiResponseDTO;
import br.ufrn.ieee.database.evento.dto.vtools.VToolsEventAttributesDTO;
import br.ufrn.ieee.database.evento.dto.vtools.VToolsEventDataDTO;
import br.ufrn.ieee.database.shared.exception.RegraDeNegocioException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class VToolsClientService {

    private final RestTemplate restTemplate;

    private static final String VTOOLS_API_URL = "https://events.vtools.ieee.org/api/public/v8/events/list?id={id}";

    // Veja que agora injetamos direto o RestTemplate! O erro vai sumir.
    public VToolsClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public VToolsEventDataDTO buscarEventoPorId(String vtoolsId) {
        try {
            ResponseEntity<VToolsApiResponseDTO> response = restTemplate.getForEntity(
                    VTOOLS_API_URL,
                    VToolsApiResponseDTO.class,
                    vtoolsId);

            VToolsApiResponseDTO body = response.getBody();

            if (body == null || body.getData() == null || body.getData().isEmpty()) {
                throw new RegraDeNegocioException("Nenhum evento encontrado no vTools com o ID: " + vtoolsId);
            }

            return body.getData().get(0);

        } catch (HttpClientErrorException e) {
            throw new RegraDeNegocioException(
                    "Erro ao consultar o vTools. Verifique se o ID está correto. Detalhe: " + e.getMessage());
        } catch (Exception e) {
            throw new RegraDeNegocioException("Falha de comunicação com o servidor do IEEE vTools: " + e.getMessage());
        }
    }
}