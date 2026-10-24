package com.antecap.cliente.client;

import com.antecap.cliente.dto.ParticipanteDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class ParticipanteClient {

    private final RestClient restClient;

    public ParticipanteClient(@Value("${api.backend.url}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl + "/participantes").build();
    }

    public List<ParticipanteDTO> listarTodos() {
        return restClient.get()
                .retrieve()
                .body(new ParameterizedTypeReference<List<ParticipanteDTO>>() {});
    }

    public ParticipanteDTO buscarPorId(Long id) {
        return restClient.get()
                .uri("/{id}", id)
                .retrieve()
                .body(ParticipanteDTO.class);
    }

    public ParticipanteDTO guardar(ParticipanteDTO dto) {
        return restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .body(ParticipanteDTO.class);
    }

    public void eliminar(Long id) {
        restClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}