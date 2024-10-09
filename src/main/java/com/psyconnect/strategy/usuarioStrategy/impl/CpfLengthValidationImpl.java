package com.psyconnect.strategy.usuarioStrategy.impl;

import org.springframework.stereotype.Component;

import com.psyconnect.dto.UsuarioDto;
import com.psyconnect.exceptions.InvalidCpfException;
import com.psyconnect.strategy.usuarioStrategy.UsuarioStrategy;

@Component
public class CpfLengthValidationImpl implements UsuarioStrategy {

	@Override
	public void validarCampos(UsuarioDto usuarioDto) {
		if(usuarioDto.cpf().length() != 11) {
			throw new InvalidCpfException("CPF tem que ter 11 digitos");
		}
		
	}

}
