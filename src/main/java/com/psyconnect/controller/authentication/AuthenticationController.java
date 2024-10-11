package com.psyconnect.controller.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psyconnect.dto.AuthenticationDto;
import com.psyconnect.security.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
	
	private final AuthService authService;
	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody AuthenticationDto data) {
		var token = authService.login(data);
		if(token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solicitação de Usuário Inválido!");
		}
		return token;
	}
}
