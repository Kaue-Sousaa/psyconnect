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
		UsuarioRoleEn role,
		boolean isProfessor,
		String confirmSenha
		
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
				entity.getRole(), 
				entity.isProfessor(), 
				entity.getConfirmSenha());
	}
}
