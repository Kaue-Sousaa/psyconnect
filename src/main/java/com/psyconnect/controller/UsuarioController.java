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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.psyconnect.dto.UsuarioDto;
import com.psyconnect.services.UsuarioService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/usuario")
@AllArgsConstructor
public class UsuarioController {

	private UsuarioService service;
	
	@GetMapping(value = "all")
	public ResponseEntity<List<UsuarioDto>> buscarTodosUsuarios(){
		var allUsuario = service.findAll();
		if(allUsuario.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(allUsuario);
	}
	
	@GetMapping(value = "{email}")
	public ResponseEntity<UsuarioDto> buscarUsuarioPorEmail(@RequestParam String email) {
		var usuarioDto = service.buscarUsuarioPorEmail(email);
		if(usuarioDto == null) {
			throw new ResourceAccessException("");
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
	
	@DeleteMapping(value = "{id}")
	public void deletarUsuario(@PathVariable Integer id) {
		service.deletarUsuario(id);
		ResponseEntity.noContent().build();
	}
}
