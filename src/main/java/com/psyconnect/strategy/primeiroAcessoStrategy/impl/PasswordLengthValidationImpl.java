package com.psyconnect.strategy.primeiroAcessoStrategy.impl;

import org.springframework.stereotype.Component;

import com.psyconnect.dto.PrimeiroAcessoDto;
import com.psyconnect.exceptions.InvalidPasswordException;
import com.psyconnect.strategy.primeiroAcessoStrategy.PrimeiroAcessoStrategy;

import lombok.SneakyThrows;

@Component
public class PasswordLengthValidationImpl implements PrimeiroAcessoStrategy{

    @SneakyThrows
    @Override
    public void validarCampos(PrimeiroAcessoDto primeiroAcessoDto) {
        if(primeiroAcessoDto.novaSenha().length() < 8 || primeiroAcessoDto.confirmSenha().length() > 12){
            throw new InvalidPasswordException("A senha deve ter no mínimo 8 caracteres e no máximo 12 caracteres.");
        }        
    }
}
