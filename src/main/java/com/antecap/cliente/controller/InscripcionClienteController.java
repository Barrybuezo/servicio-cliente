package com.antecap.cliente.controller;

import com.antecap.cliente.client.CursoClient;
import com.antecap.cliente.client.InscripcionClient;
import com.antecap.cliente.client.ParticipanteClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/inscripciones")
public class InscripcionClienteController {

    private final InscripcionClient inscripcionClient;
    private final CursoClient cursoClient;
    private final ParticipanteClient participanteClient;

    public InscripcionClienteController(InscripcionClient inscripcionClient,
                                        CursoClient cursoClient,
                                        ParticipanteClient participanteClient) {
        this.inscripcionClient = inscripcionClient;
        this.cursoClient = cursoClient;
        this.participanteClient = participanteClient;
    }

    @GetMapping
    public String listar(Model model) {
        try {
            model.addAttribute("inscripciones", inscripcionClient.listarTodas());
        } catch (Exception e) {
            model.addAttribute("inscripciones", new ArrayList<>());
            model.addAttribute("mensajeError", "No se pudo conectar con el servicio de inscripciones backend.");
        }
        return "inscripciones/list";
    }

    @GetMapping("/nueva")
    public String formularioNueva(Model model) {
        try {
            model.addAttribute("cursos", cursoClient.listarTodos());
            model.addAttribute("participantes", participanteClient.listarTodos());
        } catch (Exception e) {
            model.addAttribute("cursos", new ArrayList<>());
            model.addAttribute("participantes", new ArrayList<>());
            model.addAttribute("mensajeError", "Error al obtener la lista de cursos o participantes desde el backend.");
        }
        return "inscripciones/form";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam Long cursoId, @RequestParam Long participanteId, RedirectAttributes redirect) {
        try {
            inscripcionClient.inscribir(cursoId, participanteId);
            redirect.addFlashAttribute("mensajeExito", "Inscripción realizada con éxito");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError", "No se pudo realizar la inscripción. Verifique si el curso tiene cupo o si la inscripción ya existe.");
        }
        return "redirect:/inscripciones";
    }
}