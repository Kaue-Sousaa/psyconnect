package com.psyconnect.strategy.primeiroAcessoStrategy.impl;

import org.springframework.stereotype.Component;

import com.psyconnect.dto.PrimeiroAcessoDto;
import com.psyconnect.exceptions.InvalidPasswordException;
import com.psyconnect.strategy.primeiroAcessoStrategy.PrimeiroAcessoStrategy;

import lombok.SneakyThrows;

@Component
public class PasswordhValidationImpl implements PrimeiroAcessoStrategy{

	@Override
	@SneakyThrows
	public void validarCampos(PrimeiroAcessoDto primeriAcesso) {
		if(!validarSenha(primeriAcesso)) {
			throw new InvalidPasswordException("As senhas não são iguais. Tente novamente.");
		}
	}
	
	private boolean validarSenha(PrimeiroAcessoDto primeriAcesso) {
		return primeriAcesso.novaSenha() != null && primeriAcesso.novaSenha().equals(primeriAcesso.confirmSenha());
	}
}
