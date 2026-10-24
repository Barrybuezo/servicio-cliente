package com.antecap.cliente.controller;

import com.antecap.cliente.client.CursoClient;
import com.antecap.cliente.client.InscripcionClient;
import com.antecap.cliente.client.ParticipanteClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CursoClient cursoClient;
    private final ParticipanteClient participanteClient;
    private final InscripcionClient inscripcionClient;

    public HomeController(CursoClient cursoClient, ParticipanteClient participanteClient, InscripcionClient inscripcionClient) {
        this.cursoClient = cursoClient;
        this.participanteClient = participanteClient;
        this.inscripcionClient = inscripcionClient;
    }

    @GetMapping("/")
    public String index(Model model) {
        try {
            model.addAttribute("totalCursos", cursoClient.listarTodos().size());
            model.addAttribute("totalParticipantes", participanteClient.listarTodos().size());
            model.addAttribute("totalInscripciones", inscripcionClient.listarTodas().size());
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo conectar con el servicio API backend.");
        }
        return "index";
    }
}