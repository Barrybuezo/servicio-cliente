package com.antecap.cliente.controller;

import com.antecap.cliente.client.CursoClient;
import com.antecap.cliente.client.InscripcionClient;
import com.antecap.cliente.dto.CursoDTO;
import com.antecap.cliente.dto.InscripcionDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cursos")
public class CursoClienteController {

    private final CursoClient cursoClient;
    private final InscripcionClient inscripcionClient;

    public CursoClienteController(CursoClient cursoClient, InscripcionClient inscripcionClient) {
        this.cursoClient = cursoClient;
        this.inscripcionClient = inscripcionClient;
    }

    @GetMapping
    public String listar(Model model) {
        try {
            model.addAttribute("cursos", cursoClient.listarTodos());
        } catch (Exception e) {
            model.addAttribute("cursos", new ArrayList<>());
            model.addAttribute("mensajeError", "No se pudo conectar con el servicio de cursos backend.");
        }
        return "cursos/list";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("curso", new CursoDTO());
        return "cursos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("curso") CursoDTO curso, RedirectAttributes redirect) {
        try {
            cursoClient.guardar(curso);
            redirect.addFlashAttribute("mensajeExito", "Curso guardado exitosamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError", "Error al guardar el curso");
        }
        return "redirect:/cursos";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        try {
            CursoDTO curso = cursoClient.buscarPorId(id);
            List<InscripcionDTO> inscritos = inscripcionClient.listarTodas().stream()
                    .filter(i -> i.getCurso() != null && i.getCurso().getId().equals(id) && "ACTIVA".equals(i.getEstado()))
                    .toList();

            model.addAttribute("curso", curso);
            model.addAttribute("inscritos", inscritos);
            model.addAttribute("cuposDisponibles", curso.getCupo() - inscritos.size());
        } catch (Exception e) {
            model.addAttribute("mensajeError", "Error al cargar detalle del curso");
        }
        return "cursos/detalle";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            cursoClient.eliminar(id);
            redirect.addFlashAttribute("mensajeExito", "Curso eliminado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError", "No se puede eliminar el curso si posee inscripciones activas");
        }
        return "redirect:/cursos";
    }
}