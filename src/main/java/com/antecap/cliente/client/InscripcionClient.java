package com.antecap.cliente.client;

import com.antecap.cliente.dto.InscripcionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class InscripcionClient {

    private final RestClient restClient;

    public InscripcionClient(@Value("${api.backend.url}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl + "/inscripciones").build();
    }

    public List<InscripcionDTO> listarTodas() {
        return restClient.get()
                .retrieve()
                .body(new ParameterizedTypeReference<List<InscripcionDTO>>() {});
    }

    public InscripcionDTO inscribir(Long cursoId, Long participanteId) {
        InscripcionDTO dto = new InscripcionDTO();
        dto.setCursoId(cursoId);
        dto.setParticipanteId(participanteId);

        return restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .body(InscripcionDTO.class);
    }
}