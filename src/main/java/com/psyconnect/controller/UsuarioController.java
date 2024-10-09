package com.psyconnect.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psyconnect.dto.PrimeiroAcessoDto;
import com.psyconnect.dto.UsuarioDto;
import com.psyconnect.exceptions.ResourceNotFoundException;
import com.psyconnect.services.UsuarioService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/usuario")
@AllArgsConstructor
public class UsuarioController {

	private UsuarioService service;
	
	@GetMapping(value = "all")
	public ResponseEntity<List<UsuarioDto>> buscarTodosUsuarios(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping(value = "{email}")
	public ResponseEntity<UsuarioDto> buscarUsuarioPorEmail(@PathVariable String email) {
		var usuarioDto = service.buscarUsuarioPorEmail(email);
		if(usuarioDto == null) {
			throw new ResourceNotFoundException("Usuário não encontrado");
		}
		return ResponseEntity.ok(usuarioDto);
	}
	
	@PostMapping(value = "cadastro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> cadastrarUsuario(@RequestBody UsuarioDto usaurioDto){
		service.cadastrarUsuario(usaurioDto);
		return ResponseEntity.ok("Usuário Cadastrado com sucesso.");
	}
	
	@PutMapping(value = "atualiza", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioDto> atualizarCadastroUsuario(@RequestBody UsuarioDto usaurioDto){
		return ResponseEntity.ok(service.atualizarUsuario(usaurioDto));
	}
	
	@PostMapping(value = "/primeiro-acesso/{email}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> alterarSenhaPrimeiroAcesso(@RequestBody PrimeiroAcessoDto primeiroAcesso, @PathVariable String email){
		return ResponseEntity.ok(service.primeiroAcesso(email, primeiroAcesso));
	}
	
	@DeleteMapping(value = "{id}")
	public void deletarUsuario(@PathVariable Integer id) {
		service.deletarUsuario(id);
		ResponseEntity.noContent().build();
	}
}
