package com.psyconnect.strategy.primeiroAcessoStrategy.impl;

import org.springframework.stereotype.Component;

import com.psyconnect.dto.PrimeiroAcessoDto;
import com.psyconnect.exceptions.InvalidPasswordException;
import com.psyconnect.strategy.primeiroAcessoStrategy.PrimeiroAcessoStrategy;

import lombok.SneakyThrows;

@Component
public class PasswordRequirementsValidationImpl implements PrimeiroAcessoStrategy{

	@Override
	@SneakyThrows
	public void validarCampos(PrimeiroAcessoDto primeriAcesso) {
		  if(!primeriAcesso.novaSenha().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).+$")){
	            throw new InvalidPasswordException(
	                    "A senha deve ter pelo menos: 1 caractere minusculo, 1 caractere maiusculo, 1 numero e 1 caractere especial");
	        }
	}

}
