package com.psyconnect.dto;

import java.time.LocalDate;

import com.psyconnect.model.Aluno;

public record AlunoDto(
		Integer id,
		String nome,
		LocalDate dataNascimento,
		String habilidade,
		String dificuldade,
		String responsavel
		) {
	
	public AlunoDto(Aluno entity) {
		this(entity.getId(),
				entity.getNome(),
				entity.getDataNascimento(),
				entity.getHabilidade(),
				entity.getDificuldade(),
				entity.getResponsavel());
	}
}
