package com.psyconnect.strategy.usuarioStrategy.impl;

import org.springframework.stereotype.Component;

import com.psyconnect.dto.UsuarioDto;
import com.psyconnect.exceptions.ExistingObjectException;
import com.psyconnect.repositories.UsuarioRepository;
import com.psyconnect.strategy.usuarioStrategy.UsuarioStrategy;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@Component
@AllArgsConstructor
public class ExistingCpfValidationImpl implements UsuarioStrategy{

	private final UsuarioRepository userRepository;
	
	@Override
	@SneakyThrows
	public void validarCampos(UsuarioDto usuarioDto) {
		if(!isValidCpf(usuarioDto.cpf())) {
			throw new ExistingObjectException("CPF já cadastrado!"); 
		}
	}
	
	private boolean isValidCpf(String cpf){
	    return userRepository.findAll().stream()
	            .noneMatch(user -> user.getCpf().equals(cpf));
	}
}
