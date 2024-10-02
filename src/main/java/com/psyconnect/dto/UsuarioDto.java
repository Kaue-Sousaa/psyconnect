package com.psyconnect.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.psyconnect.enums.UsuarioRoleEn;
import com.psyconnect.model.Usuario;

public record UsuarioDto (
		
		Integer id,
		LocalDateTime dataInclusao,
		LocalDateTime dataFinalizacao,
		String nome,
		String email,
		String cpf,
		LocalDate dataNascimento,
		String telefone,
		String senha,
		String confirmSenha,
		UsuarioRoleEn role,
		boolean isProfessor
		
		) {
	
	public UsuarioDto(Usuario entity) {
		this(
				entity.getId(), 
				entity.getDataInclusao(), 
				entity.getDataFinalizacao(), 
				entity.getNome(), 
				entity.getEmail(),
				entity.getCpf(),
				entity.getDataNascimento(), 
				entity.getTelefone(), 
				entity.getSenha(), 
				entity.getConfirmSenha(),
				entity.getRole(),
				entity.isProfessor()); 
	}
}
