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
public class ExistingEmailValidationImpl implements UsuarioStrategy{
	
	private final UsuarioRepository userRepository;
	
	@Override
	@SneakyThrows
	public void validarCampos(UsuarioDto usuarioDto) {
		if(!isValidEmail(usuarioDto.email())) {
			throw new ExistingObjectException("Email já cadastrado!");  
		}
	}
	
	private boolean isValidEmail(String email) {
		return userRepository
				.findAll()
				.stream()
				.noneMatch(user -> user.getEmail().equals(email));
	}

}
