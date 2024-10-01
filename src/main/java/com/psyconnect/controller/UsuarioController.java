package com.psyconnect.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psyconnect.dto.UsuarioDto;

@RestController
@RequestMapping("v1/usuario")
public class UsuarioController {

	
	@GetMapping(value = "/all")
	public ResponseEntity<UsuarioDto> findAll(){
		return null;
	}
}
