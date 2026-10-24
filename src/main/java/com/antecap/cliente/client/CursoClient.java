package com.antecap.cliente.client;

import com.antecap.cliente.dto.CursoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class CursoClient {

    private final RestClient restClient;

    public CursoClient(@Value("${api.backend.url}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl + "/cursos").build();
    }

    public List<CursoDTO> listarTodos() {
        return restClient.get()
                .retrieve()
                .body(new ParameterizedTypeReference<List<CursoDTO>>() {});
    }

    public CursoDTO buscarPorId(Long id) {
        return restClient.get()
                .uri("/{id}", id)
                .retrieve()
                .body(CursoDTO.class);
    }

    public CursoDTO guardar(CursoDTO dto) {
        return restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .body(CursoDTO.class);
    }

    public void eliminar(Long id) {
        restClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}