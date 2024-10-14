package com.psyconnect.security;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.psyconnect.dto.AuthenticationDto;
import com.psyconnect.exceptions.EmailInvalidoException;
import com.psyconnect.exceptions.PrimeiroAcessoException;
import com.psyconnect.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final UsuarioRepository repository;
	private final TokenService tokenService;

	public ResponseEntity<?> login(AuthenticationDto login) {
	    authentication(login);
	    var usuario = repository.findByEmail(login.email());
	    
	    if (usuario == null) {
	        throw new UsernameNotFoundException("Email " + login.email() + " não encontrado!");
	    }
	    if (usuario.getPrimeiroAcesso() == null) {
	        throw new PrimeiroAcessoException("É necessário alterar a senha no primeiro acesso.");
	    }
	    return ResponseEntity
	    		.ok(tokenService.createAccessToken
	    				(login.email(), List.of(usuario.getRole()), usuario));
	}

	private void authentication(AuthenticationDto auth) {
		if (!isEmailValido(auth.email())) {
			throw new EmailInvalidoException("Email inválido!");
		}
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(auth.email(), auth.senha()));
	}

	private boolean isEmailValido(String email) {
	    return email != null && 
	    		email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
	}
}
