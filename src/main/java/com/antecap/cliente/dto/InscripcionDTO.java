package com.antecap.cliente.dto;

import java.time.LocalDate;

public class InscripcionDTO {
    private Long id;
    private Long cursoId;
    private Long participanteId;
    private CursoDTO curso;
    private ParticipanteDTO participante;
    private LocalDate fechaInscripcion;
    private String estado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }
    public Long getParticipanteId() { return participanteId; }
    public void setParticipanteId(Long participanteId) { this.participanteId = participanteId; }
    public CursoDTO getCurso() { return curso; }
    public void setCurso(CursoDTO curso) { this.curso = curso; }
    public ParticipanteDTO getParticipante() { return participante; }
    public void setParticipante(ParticipanteDTO participante) { this.participante = participante; }
    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDate fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}