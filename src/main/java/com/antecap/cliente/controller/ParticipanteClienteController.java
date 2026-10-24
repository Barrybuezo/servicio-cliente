package com.antecap.cliente.controller;

import com.antecap.cliente.client.ParticipanteClient;
import com.antecap.cliente.dto.ParticipanteDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/participantes")
public class ParticipanteClienteController {

    private final ParticipanteClient participanteClient;

    public ParticipanteClienteController(ParticipanteClient participanteClient) {
        this.participanteClient = participanteClient;
    }

    @GetMapping
    public String listar(Model model) {
        try {
            model.addAttribute("participantes", participanteClient.listarTodos());
        } catch (Exception e) {
            model.addAttribute("participantes", new ArrayList<>());
            model.addAttribute("mensajeError", "No se pudo conectar con el servicio de participantes backend.");
        }
        return "participantes/list";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("participante", new ParticipanteDTO());
        return "participantes/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("participante") ParticipanteDTO participante, RedirectAttributes redirect) {
        try {
            participanteClient.guardar(participante);
            redirect.addFlashAttribute("mensajeExito", "Participante guardado exitosamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError", "Error al guardar el participante (verifique que el correo no esté duplicado)");
        }
        return "redirect:/participantes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            participanteClient.eliminar(id);
            redirect.addFlashAttribute("mensajeExito", "Participante eliminado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError", "Error al eliminar el participante o posee datos asociados");
        }
        return "redirect:/participantes";
    }
}